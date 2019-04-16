
$(document).ready(function () {
  $('.fa-pencil-alt').hover(function () {
    $(this).toggleClass("text-primary").toggleClass("text-dark");
  }
  )

  $('.fa-trash-alt').hover(function () {
    $(this).toggleClass("text-danger").toggleClass("text-dark");
  }
  )
});