let userApp=new Vue({
    el:"#userApp",
    data:{
        user:{}
    },
    methods:{
        loadCurrentUser(){

            axios({
                url:"/v1/users/me",
                method:"get"
            }).then(function (response) {
                userApp.user=response.data;
            })
        }
    },
    created(){
        this.loadCurrentUser();
    }

})