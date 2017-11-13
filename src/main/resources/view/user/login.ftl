<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <script type="text/javascript" src="/jquery/jquery-2.2.3.min.js"></script>
    <link rel="stylesheet" href="/layui/css/layui.css"  media="all">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
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


    <div class="layui-row" style="margin-top: 300px;">

        <div class="layui-col-md4 layui-col-md-offset4 " style="min-height: 260px;">
            <div id="logo" class="layui-anim" style="text-align: center;padding: 30px 0;display: none">
                <img src="/images/logo.png">
            </div>

            <div id="login" class="layui-anim" style="display: none">
                <form id="form" class="layui-form" style="padding: 0 35px;">
                    <div class="layui-form-item">
                        <div class="layui-input-block"  style="color:white;">
                            <input type="text" name="username"
                                   placeholder="请输入用户名"  class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <input type="password" name="password"
                                   placeholder="请输入密码"  class="layui-input">
                        </div>
                    </div>


                    <input type="hidden" name="rememberMe" value="on" >

                </form>

                <div class="layui-form-item" style="padding: 0 35px;">
                    <div class="layui-input-block">
                        <button class="layui-btn" style="width: 100%" onclick="loginIn()">登录</button>
                    </div>
                </div>

                <div class="layui-form-item" style="padding: 0 35px;text-align: right">
                    <a href="/register">注册</a> <span style="color: #ccc"> | </span> <a>忘记密码</a>
                </div>
            </div>


        </div>


    </div>


<script src="/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
    <script>
        //Demo
        layui.use('form', function(){
            var form = layui.form;

            //监听提交
//            form.on('submit(formDemo)', function(data){
//                return false;
//            });
        });

        function loginIn(){
            $.ajax({
                url:"/login",
                data:$("#form").serialize(),
                success:function(data){
                    if(data==="fail"){
                        layer.msg("用户名或密码错误");
                    }else{
                        layer.msg("登录成功");
                        setTimeout(function(){
                            location.href="${returnUrl!'/'}";
                        },500)
                    }
                }
            })
        }

        $(document).ready(function(){
            setTimeout(function(){
                $("#logo").css("display","block");
                $("#logo").addClass("layui-anim-upbit");
            },500)

            setTimeout(function(){
                $("#login").css("display","block");
                $("#login").addClass("layui-anim-upbit");
            },1000)


        })


    </script>

</body>
</html>