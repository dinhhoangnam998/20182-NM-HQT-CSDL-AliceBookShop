$(document).ready(function () {

  $('#search-box').on("keyup focus", function (event) {
    var key = $('#search-box').val();
    if (key != "") {
      getSearchResult(key);
    } else {
      $('#search-result').hide();
    }

  })

  // $('#search-box').on("blur", function () {
  //   $('#search-result').hide();
  // })

  $('#navbar').click(function () {
    $('#search-result').hide();
  })

  $('#ss-top-carousel').on("click", function () {
    $('#search-result').hide();
  })

  function getSearchResult(key) {
    $.getJSON("/books?key=" + key,
      function (books, textStatus, jqXHR) {
        if (textStatus == "success") {
          var content = "";
          content += '<div class="list-group">';

          var limit = (books.length > 5) ? (5) : (books.length);
          for (let i = 0; i < limit; i++) {
            const book = books[i];
            content += '<a href="/books/' + book.id + "#navbar" +
              '" class="list-group-item list-group-item-action py-2">' +
              '<img src="' + book.imgURL + '" alt="" style="width: 30px; height: 40px; object-fit: scale-down; margin-right: 10px;">' +
              book.name +
              '</a>';
          }

          if (books.length > 5) {
            content += '<a href="books/search?key=' + key + '" class="list-group-item list-group-item-action">Xem thêm kết quả</a>';
          }

          if (books.length == 0) {
            content += '<a href="#" class="list-group-item list-group-item-action">Không tìm thấy kết quả phù hợp.</a>';
          }

          content += '</div>';

          $('#search-result').html(content);
          $('#search-result').show();
        }
      }
    );
  }
});
