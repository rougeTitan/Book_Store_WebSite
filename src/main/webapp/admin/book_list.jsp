<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Manage Books - Evergreen Bookstore Administration</title>
    <link rel="stylesheet" href="../css/style.css">
    <script type="text/javascript" src="../js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="../js/jquery.validate.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.3.slim.min.js"
            integrity="sha256-ZwqZIVdD3iXNyGHbSYdsmWP//UBokj2FHAxKuSBKDSo="
            crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div align="center" style="padding-top: 20px;padding-bottom: 20px;">
    <h2 class="pageheading">Book Management</h2>
    <a href="book_form.jsp">Create New Book</a>
</div>

<c:if test="${Message != null}">
    <div align="center">
        <h4 class="message">${message}</h4>
    </div>
</c:if>

<div align="center">
    <table border="1" cellpadding="5" style="border: solid black 1px;">
        <tr style="background-color: cornflowerblue;">
            <th>Index</th>
            <th>ID</th>
            <th>Image</th>
            <th>Title</th>
            <th>Author</th>
            <th>Category</th>
            <th>Price</th>
            <th>Last Updated</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="book" items="${listBooks}" varStatus="status">
            <tr>
                <td>${status.index+1}</td>
                <td>${book.bookId}</td>
                <td><img src="data:image/jpg;base64,${book.base64Image}image" width="84" height="110"/></td>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>${book.category.name}</td>
                <td>${book.price}</td>
                    <%--<td>${book.lastUpdateTime}</td>--%>
                <td><fmt:formatDate pattern="MM/dd/yyyy" value='${book.lastUpdateTime}'/></td>
                <td><a href="edit_book?id=${book.bookId}">Edit</a>&nbsp;
                    <a href="javascript:void(0);" class="deleteLink" id="${book.bookId}">Delete</a>&nbsp;
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<footer style="bottom: 0;left: 36%;">
    <jsp:include page="footer.jsp"/>
</footer>

<script type="text/javascript">
    $(document).ready(function () {
        $(".deleteLink").each(function () {
            $(this).on("click", function () {
                let bookId = $(this).attr("id");
                if (confirm("Are you sure you want to delete the book with id " + bookId + "?")) {
                    window.location = "delete_book?id=" + bookId;
                }
            });
        });
    });
</script>

</body>

</html>
