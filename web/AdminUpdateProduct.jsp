<%-- 
    Document   : AdminUpdateProduct
    Created on : Nov 19, 2020, 3:07:01 PM
    Author     : Administrator
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <title>Adm display product</title>
    </head>
    <body>
        <div class="container">

            <h2 class="my-5">Update Product</h2>
            
            <form class="well form-horizontal" action="admin-update-product" method="post"  id="contact_form">

                    <div class="form-group">
                        <label class="col-md-4 control-label">ID</label>  
                        <div class="col-md-4 inputGroupContainer">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                <input  name="pID" placeholder="ID" class="form-control" value="${p.id}"  type="text" readonly >
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">Name</label>  
                        <div class="col-md-4 inputGroupContainer">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                <input  name="pName" placeholder="First Name" class="form-control" value="${p.name}"  type="text">
                            </div>
                        </div>
                    </div>

                    <div class="form-group"> 
                        <label class="col-md-4 control-label">Category ID</label>
                        <div class="col-md-4 selectContainer">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-list"></i></span>
                                <select name="pCategory" class="form-control selectpicker">
                                    <c:forEach items="${requestScope.listCategory}" var="cate">
                                        <option value="${cate.id}" ${p.id eq cate.id? "selected" : ""}>${cate.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <!-- Text input-->

                        <div class="form-group">
                            <label class="col-md-4 control-label" >Price</label> 
                            <div class="col-md-4 inputGroupContainer">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input name="pPrice" placeholder="Price" class="form-control" value="${p.price}"  type="text">
                                </div>
                            </div>
                        </div>


                    </div>


                    <div class="form-group">
                        <label class="col-md-4 control-label">Description</label>  
                        <div class="col-md-4 inputGroupContainer">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                <input  name="pDescription" placeholder="Description" class="form-control" value="${p.description}"  type="text">
                            </div>
                        </div>
                    </div>


                    <!-- Button -->
                    <div class="form-group">
                        <label class="col-md-4 control-label"></label>
                        <div class="col-md-4"><br>
                            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<button type="submit" class="btn btn-warning" >&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspSUBMIT <span class="glyphicon glyphicon-send"></span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</button>
                        </div>
                    </div>

            </form>
        </div>
    </body>
</html>
