let quenstionApp=new Vue({
    el:'#questionApp',
    data:{
        question:{}
    },
    methods:{
        loadQuestion(){
            let qid=location.search;
            //如果qid
            if(!qid){
                return;
            }
            qid=qid.substring(1);
            axios({
                // v1/questions/153
                url:'/v1/questions/'+qid,
                methods: 'get'
            }).then(function (response) {
                quenstionApp.question=response.data;
                quenstionApp.updateDuration();
                addDuration(quenstionApp.question);
            })
        },
        updateDuration(){
            //创建问题时候的时间毫秒数
            let createtime = new Date(this.question.createtime).getTime();
            //当前时间毫秒数
            let now = new Date().getTime();
            let duration = now - createtime;
            if (duration < 1000*60){ //一分钟以内
                this.question.duration = "刚刚";
            }else if(duration < 1000*60*60){ //一小时以内
                this.question.duration =
                    (duration/1000/60).toFixed(0)+"分钟以前";
            }else if (duration < 1000*60*60*24){
                this.question.duration =
                    (duration/1000/60/60).toFixed(0)+"小时以前";
            }else {
                this.question.duration =
                    (duration/1000/60/60/24).toFixed(0)+"天以前";
            }
        }
    },
    created(){
        this.loadQuestion();
    }
    //stackoverflow
})


let postAnswerApp=new Vue({
    el:"#postAnswerApp",
    data: {
        message:{},
        hasError:false
    },
    methods:{
        postAnswer(){
            //判断url有id
            //判断有内容
            let qid=location.search;
            if(!qid){
                this.message="必须包含问题id";
            }
            qid=qid.substring(1);
            //
            let content=$("#summernote").val();
            if(!content){
                this.message="回答内容不能为空!";
                this.hasError=true;
                return;
            }
            let form=new FormData();
            form.append("questionId",qid);
            form.append("content",content);
            axios({
                url: 'v1/answers',
                method:'post',
                data:form
            }).then(function (response) {
                console.log(response.data);
                //新增成功之后的操作
                let answer=response.data;
                //我们需要
                answersApp.answers.push(answer);
                $("#summernote").summernote("reset");
                postAnswerApp.hasError=false;
                answer.duration="刚刚";
            })
        },
        postComment(answerId){
            axios({
                url:"/v1/comments",
                method:"post",
                data:form
            }).then(function(response){
                console.log(response.data);
                //清空新增的文字
                textarea.val("");
                //将弹出的输入框折叠隐藏
                $("#addComment"+answerId).collapse("hide");
                //实现新增的评论立即显示在页面上
                //原理是将新增的评论对象添加到对应的回答的评论数组中
                //定义一个变量保存我么新增成功的评论
                let comment=response.data;
                //定义一个变量引用所有回答
                let answers=answersApp.answers;
                // 遍历所有回答
                for(let i=0;i<answers.length;i++){
                    //判断当前answers[i]是不是本次新增评论的回答
                    if(answers[i].id == answerId){
                        //如果是,将本次新增的评论添加到当前回答的评论数组中
                        answers[i].comments.push(comment);
                        return;
                    }
                }
            })
        },
        removeComment:function(commentId,index,comments){
            //如果commentId不存在
            if(!commentId)
                return;
            axios({
                //   /v1/comments/{id}/delete
                url:"/v1/comments/"+commentId+"/delete",
                method:"get"
            }).then(function(response){
                console.log(response.data);
                if(response.data!="删除成功"){
                    alert(response.data);
                }else {
                    //删除成功,从数组中移除指定位置元素
                    //js代码中提供了删除数组中指定位置元素的api
                    //splice([从数组中的哪个索引开始删],[删除几个])
                    comments.splice(index,1)
                }
            })
        }
    }

})

//查询显示所有回答的
let answersApp=new Vue({
    el:"#answersApp",
    data:{
        answers:[]
    },
    methods:{
        loadAnswers(){
            let qid=location.search;
            if(!qid){
                alert('');
                return;
            }
            qid=qid.substring(1);
            axios({
                url:"/v1/answers/question/"+qid,
                methods:'get'
            }).then(function (response) {
                answersApp.answers=response.data;
                for(let i=0;i<answers;i++){
                    addDuration(answers[i]);
                }
            })
        }
    },
    created(){
        this.loadAnswers();
    }
})