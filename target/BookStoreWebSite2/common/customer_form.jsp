<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table>
    <tr>
        <td align="right">E-mail</td>
        <td align="left"><input type="text" id="email" name="email" size="45" value="${customer.email}"/></td>
    </tr>
    <tr>
        <td align="right">First Name:</td>
        <td align="left"><input type="text" id="firstname" name="firstname" size="45" value="${customer.firstname}"/>
        </td>
    </tr>
    <tr>
        <td align="right">Last Name:</td>
        <td align="left"><input type="text" id="lastname" name="lastname" size="45" value="${customer.lastname}"/></td>
    </tr>
    <tr>
        <td align="right">Password:</td>
        <td align="left"><input type="text" id="password" name="password" size="45" value="${customer.password}"/></td>
    </tr>
    <tr>
        <td align="right">Confirmed Password:</td>
        <td align="left"><input type="text" id="confirmPassword" name="confirmPassword" size="45" value="${customer.password}"/></td>
    </tr>
    <tr>
        <td align="right">Phone Number:</td>
        <td align="left"><input type="text" id="phone" name="phone" size="45" value="${customer.phone}"/></td>
    </tr>
    <tr>
        <td align="right">Address Line 1:</td>
        <td align="left"><input type="text" id="addressLine1" name="addressLine1" size="45" value="${customer.addressLine1}"/></td>
    </tr>
    <tr>
        <td align="right">Address Line 2:</td>
        <td align="left"><input type="text" id="addressLine2" name="addressLine2" size="45" value="${customer.addressLine2}"/></td>
    </tr>
    <tr>
        <td align="right">City:</td>
        <td align="left"><input type="text" id="city" name="city" size="45" value="${customer.city}"/></td>
    </tr>
    <tr>
        <td align="right">State:</td>
        <td align="left"><input type="text" id="state" name="state" size="45" value="${customer.state}"/></td>
    </tr>
    <tr>
        <td align="right">Zip Code:</td>
        <td align="left"><input type="text" id="zipCode" name="zipCode" size="10" value="${customer.zipcode}"/></td>
    </tr>
    <tr>
        <td align="right">Country:</td>
        <td align="left">
            <select name="country" id="country">
                <c:forEach items="${mapCountries}" var="country">
                    <option value="${country.value}" <c:if test="${customer.country eq country.value}">selected="selected"</c:if>>${country.key}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr>
        <tr><td>&nbsp;</td></tr>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <button type="submit">Save</button>&nbsp;&nbsp;&nbsp;
            <input type="button" value="Cancel" onclick="history.go(-1);"/>
        </td>
    </tr>

</table>
