$(document).ready(function () {
  $('#navbar-cart-icon').click(function () {
    var link = 'http://localhost:8080/carts/' + [[(${ session.cart } != null) ? (${ session.cart.id }): (-1)]];
$.getJSON(link,
  function (cart, textStatus, jqXHR) {

    if (textStatus == "success") {
      var content = "";
      var ols = cart.orderLines;
      for (let i = 0; i < ols.length; i++) {
        const ol = ols[i];
        content += '<tr><td>' + (i + 1) + '</td><td>' + ol.book.name + '</td><td>' + ol.quantity + '</td></tr>';
      }

      $('#table-body').html(content);

    }
  }
);
      })
    });