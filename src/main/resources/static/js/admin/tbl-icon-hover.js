
$(document).ready(function () {
  $('.fa-pencil-alt').hover(function () {
    $(this).toggleClass("text-warning").toggleClass("text-dark");
  }
  )

  $('.fa-trash-alt').hover(function () {
    $(this).toggleClass("text-danger").toggleClass("text-dark");
  }
  )
});