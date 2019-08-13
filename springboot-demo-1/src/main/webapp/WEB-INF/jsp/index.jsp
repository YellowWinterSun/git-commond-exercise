<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

</head>
<body>
	<div class="container">

		<div class="row">
			<div class="page-header" style="margin-top:100px">
			  <h1 class="text-center" style="color:#337ab7">SpringBoot2.x <small>DEMO</small><img style="width: 50px;height:50px;" src="/static/image/userIcon.jpg"/> </h1>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12">
				<h1 class="text-center" >以下两个IP，是用nginx做负载均衡时，用于获取客户端真实ip地址以及nginxIp地址的</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<h1 class="text-center" >X-real-ip: <%=request.getHeader("X-real-ip")%></h1>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<h1 class="text-center" >nginx ip: <%=request.getRemoteAddr()%></h1>
			</div>
		</div>

		<div class="row" style="color:#337ab7">

			<div class="col-lg-offset-3 col-lg-6">
				<form class="form-horizontal" style="margin-top:20px">
				  <div class="form-group">
				    <label for="userId" class="col-sm-2 control-label">用户名id</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" id="userId" name="userId" placeholder="userId">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="col-sm-offset-2 col-sm-3">
				      <button type="button" class="btn btn-primary" id="loginBtn">登录</button>
				    </div>
					  <div class="col-sm-3">
						  <button type="button" class="btn btn-warning" id="validBtn">验证</button>
					  </div>
					  <div class="col-sm-3">
						  <button type="button" class="btn btn-default" id="logoutBtn">注销</button>
					  </div>
				  </div>
				</form>
			</div>
		</div>

		<div class="row" style="color:#337ab7">

			<div class="col-lg-12">
				<ul>
					<li><a target="_blank" href="/demo/testMapper">/demo/testMapper</a> </li>
					<li><a target="_blank" href="/demo/testMybatisPlus">/demo/testMybatisPlus</a> </li>
					<li><a target="_blank" href="/rocketmq/testSend">/rocketmq/testSend</a> </li>
				</ul>

			</div>
		</div>

		
		
	</div>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/static/jquery/jquery2.0.3.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

	<script>
	
	$(document).ready(function(){


		$("#loginBtn").click(function(){
		
			var id = $("#userId").val().trim();
			if(id=="")
			{
				alert("帐号未输入");
				return;
			}
			
			$.ajax({
				url:"/login/doLogin",
				data:{
					userId : id
				},
				type:"post",
				success:function(msg){
					console.log("agax result: " + msg);
				},
				error:function(msg){
					alert("异常");
				}
			});
		});

        $("#validBtn").click(function(){


            $.ajax({
                url:"/login/validLogin",
                data:{
                },
                type:"post",
                success:function(msg) {
                    if (msg.code == undefined) {
                        console.log("ajax result: " + msg);
                    }
                    else {
                        console.log("ajax result: " + msg.msg);
                    }

                },
                error:function(msg){
                    alert("异常");
                }
            });
        });

        $("#logoutBtn").click(function(){


            $.ajax({
                url:"/login/logout",
                data:{
                },
                type:"post",
                success:function(msg){
                    console.log("agax result: " + msg);
                },
                error:function(msg){
                    alert("异常");
                }
            });
        });
		
	});

	</script>
</body>
</html>