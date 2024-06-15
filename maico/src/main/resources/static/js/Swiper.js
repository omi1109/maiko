const mySwiper = new Swiper('.swiper', {
  // Optional parameters
  loop: true,
  autoplay: {     //追記
        delay: 1000,   //追記
    },
     speed: 4000,
  // If we need pagination
  pagination: {
    el: '.swiper-pagination',
  },
 
  // Navigation arrows
  navigation: {
    nextEl: '.swiper-button-next',
    prevEl: '.swiper-button-prev',
  },
 
  // And if we need scrollbar
  scrollbar: {
    el: '.swiper-scrollbar',
  },
});