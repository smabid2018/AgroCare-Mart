/**
 * 
 */
/*$('body').mousemove(function(e) {
    var amountMovedX = (e.pageX * -0.53 / 6);
    var amountMovedY = (e.pageY * -0.53 / 6);
    $('.card').css('left', amountMovedX + 'px');
     $('.card').css('top', amountMovedY + 'px');
 });*/
 
/*$('body').mousemove(function(e) {
    var amountMovedX = (e.pageX * -0.53 / 6);
    var amountMovedY = (e.pageY * -0.53 / 6);
    $('.card').stop().animate({
        left: amountMovedX,
        top: amountMovedY
    }, 100, 'linear'); // Adjust the duration (100ms) and easing ('linear') as needed
});
*/

$(document).ready(function() {
  var $slider = $('div#slider figure');
  var moveSlider = function() {
    var currentLeft = parseInt($slider.css('left'), 10);
    var newLeft = currentLeft - 1; // Adjust the pixel shift per frame as needed
    $slider.css('left', newLeft + 'px');

    // Reset position when end is reached
    if (Math.abs(newLeft) >= $slider.width() / 5) {
      $slider.css('left', '0');
    }
  };

  // Set interval for the continuous movement
  setInterval(moveSlider, 90); // Adjust interval for smoothness and speed

  // Mousemove parallax effect
  $('body').mousemove(function(e) {
    var amountMovedX = (e.pageX * -1 / 12);
    var amountMovedY = (e.pageY * -1 / 12);
    $('.card').stop().animate({
      left: amountMovedX,
      top: amountMovedY
    }, 100, 'linear');
  });
});
