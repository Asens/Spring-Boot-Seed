<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <script type="text/javascript" src="/jquery/jquery-2.2.3.min.js"></script>
    <script type="text/javascript" src="/jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/jquery/notify.js"></script>
    <link rel="stylesheet" href="/layui/css/layui.css"  media="all">
    <style>
        #form .error{
            color:#d73435;
        }

        .layui-layer .layui-layer-btn{
            padding: 0;
        }

        .layui-layer.layui-layer-dialog .layui-layer-content{
            padding: 4px 8px;
            font-size: 12px;
        }

        .layui-layer .layui-layer-btn{
            box-shadow: 0 2px 8px rgba(0,0,0,.2);
            border-radius: 4px;
        }

    </style>
</head>

<style>
    .site-h1 {
        margin-bottom: 20px;
        line-height: 60px;
        padding-bottom: 10px;
        color: #393D49;
        border-bottom: 1px solid #eee;
        font-size: 22px;
        font-weight: 300;
        text-align: center;
    }

    .layui-form-checkbox[lay-skin=primary] span {
        color: white;
    }

    .layui-input-block {
        margin-left: 0;
    }

    .layui-form-item a{
        color: #ccc;
        cursor: pointer;
    }
</style>
<body style="background-color:#333;">



<div class="layui-container">


    <div class="layui-row" style="margin-top: 200px;">

        <div  class="layui-col-md4 layui-col-md-offset4 layui-anim" style="min-height: 320px;">
            <div id="logo" class="layui-anim" style="text-align: center;padding: 30px 0;display: none">
                <img src="/images/logo.png">
            </div>

            <div id="register" class="layui-anim" style="display: none">
                <form id="form" class="layui-form" action="/register" method="post" style="padding: 0 35px;">

                    <div class="layui-form-item">
                        <div class="layui-input-block"  style="color:white;">
                            <input type="text" name="email" id="email"
                                   placeholder="请输入邮箱"  class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-inline"  style="color:white;">
                            <input type="text" name="captcha" id="captcha"
                                   placeholder="请输入验证码"  class="layui-input">
                        </div>
                        <div>
                            <button type="button" class="layui-btn layui-btn-normal" onclick="getCode($(this))">
                            获取验证码
                            </button>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-block"  style="color:white;">
                            <input type="text" name="username"
                                   placeholder="请输入用户名"  class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <input type="password" name="password" id="password"
                                   placeholder="请输入密码"  class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <input type="password" name="repassword"
                                   placeholder="请确认密码"  class="layui-input">
                        </div>
                    </div>


                </form>

                <div class="layui-form-item" style="padding: 0 35px;">
                    <div class="layui-input-block">
                        <button class="layui-btn" style="width: 100%" onclick="register()">注册</button>
                    </div>
                </div>

            </div>

            <div id="login" class="layui-form-item layui-anim" style="padding: 0 35px;text-align: right;display: none">
                <a href="/signIn">已有账号，登录</a>
            </div>

        </div>


    </div>


    <script src="/layui/layui.js" charset="utf-8"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
    <script>
        //Demo
        layui.use('form', function(){
            var form = layui.form;

        });

        function getCode(cur){
            if(cur.hasClass("layui-btn-disabled")){
                return;
            }
            var b=validate.element($("#email"));
            if(!b){
                notify.error("请先输入正确的邮箱");
                return;
            }

            $.ajax({
                url:"/getCode",
                data:{
                    email:$("#email").val()
                },
                success:function(data){
                    if (data === 'success') {
                        notify.success("验证码发送成功！请查看邮件并填写收到的验证码");
                        var t=59;
                        var interval;
                        cur.html(t+"秒");
                        cur.addClass("layui-btn-disabled");
                        interval=setInterval(function(){
                            cur.html(--t+"秒");

                            if(t<=0){
                                clearInterval(interval);
                                cur.html("获取验证码");
                                cur.removeClass("layui-btn-disabled");
                            }
                        },1000);
                    }else if(data==="later"){
                        notify.info("系统繁忙，请稍后再试")
                    }else if(data==="limit"){
                        notify.info("今日注册已达到上限，请明天再试")
                    }else{
                        notify.warn("验证码发送失败！请确认邮箱填写是否正确")
                    }
                },
                error:function(){
                    notify.error('网络错误，请稍候再试');
                }
            });


        }

        var validate=$("#form").validate({
            rules: {
                username: {
                    required: true,
                    minlength:2
                },
                email: {
                    required: true,
                    email:true,
                    remote:"/hasEmail"
                },
                captcha:{
                    required: true,
                    minlength:6,
                    maxlength:6,
                    remote:{
                        url:"/validateCode",
                        data:{
                            email: function() {
                                return $("#email").val();
                            },
                            code: function() {
                                return $("#captcha").val();
                            }
                        }
                    }
                },
                password:{
                    required: true,
                    minlength:6
                },
                repassword:{
                    required: true,
                    minlength:6,
                    equalTo:"#password"
                }
            },
            messages:{
                username:{
                    required:'请输入用户名',
                    minlength:"用户名最小长度为2"
                },
                email: {
                    required: '请输入邮箱',
                    email:"请输入正确的邮箱",
                    remote:"该邮箱已被注册"
                },
                captcha:{
                    required: '请输入验证码',
                    minlength:"验证码为6位数字",
                    maxlength:"验证码为6位数字",
                    remote:"验证码错误"
                },
                password:{
                    required: '请输入密码',
                    minlength:"密码最小长度为6"
                },
                repassword:{
                    required: '请再次输入密码',
                    minlength:"密码最小长度为6",
                    equalTo:"两次密码输入不一致"
                }

            },
            submitHandler: function(form) {
                $.ajax({
                    url:"/register",
                    method:"POST",
                    data:$("#form").serialize(),
                    success:function(body){
                        if (body.status === 'success') {
                            notify.success('注册成功,前往登录页面');
                            location.href="http://localhost:8081/signIn"
                        } else {
                            notify.error('注册失败，请稍候再试');
                        }
                    }
                })
            }
        });


        function register(){
            $("#form").submit();
        }

        $(document).ready(function(){
            setTimeout(function(){
                $("#logo").css("display","block");
                $("#logo").addClass("layui-anim-upbit");
            },500)

            setTimeout(function(){
                $("#register").css("display","block");
                $("#register").addClass("layui-anim-upbit");
            },1000)

            setTimeout(function(){
                $("#login").css("display","block");
                $("#login").addClass("layui-anim-upbit");
            },1500)


        })

    </script>

</body>
</html>