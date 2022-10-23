$(function () {
  const main = () => {
    //메인비주얼
    var interleaveOffset = 0.5;

/*
    var mainVisual = new Swiper(".vis_slider .swiper-container", {
      slidesPerView: "auto",
      loop: true,
      // loopedSlides: 3,
      speed: 1000,
      spaceBetween: 24,
      grabCursor: true,
      watchSlidesProgress: true,
      mousewheelControl: true,
      keyboardControl: true,
      autoplay: {
        delay: 5000,
      },
      pagination: {
        el: ".vis_slider .swiper-pagination", //12.17 jhj 수정
        type: "fraction",
      },
      navigation: {
        nextEl: ".vis_slider .next",
        prevEl: ".vis_slider .prev",
      },
      breakpoints: {
        500: {
          slidesPerView: 2,
          spaceBetween: 24,
        },
        1240: {
          slidesPerView: 5,
          spaceBetween: 24,
        },
      },
      on: {
        progress: function () {
          var swiper = this;
          for (var i = 0; i < swiper.slides.length; i++) {
            var slideProgress = swiper.slides[i].progress;
            var innerOffset = swiper.width * interleaveOffset;
            var innerTranslate = slideProgress * innerOffset;
            //swiper.slides[i].querySelector(".slide").style.transform ="translate3d(" + innerTranslate + "px, 0, 0)";
          }
        },
        slideChangeTransitionStart: function (slider) {
          $(".swiper-progress").removeClass("full");
        },
        slideChangeTransitionEnd: function (slider) {
          $(".swiper-progress").addClass("full");
        },
        touchStart: function () {
          var swiper = this;
          for (var i = 0; i < swiper.slides.length; i++) {
            swiper.slides[i].style.transition = "";
          }
        },
        setTransition: function (speed) {
          var swiper = this;
          for (var i = 0; i < swiper.slides.length; i++) {
            swiper.slides[i].style.transition = speed + "ms";
            swiper.slides[i].querySelector(".slide").style.transition =
              speed + "ms";
          }
        },
      },
    });
*/
    $(".vis_slider .swiper-slide a").focusin(function () {
      mainVisual.slideTo($(this).parent().index(), 1000);
      return false;
    });

    $(".vis_slider .play-stop").click(function () {
      $(this).toggleClass("play");
      if ($(this).hasClass("play")) {
        mainVisual.autoplay.stop();
      } else {
        mainVisual.autoplay.start();
      }
    });

    // 강좌 분류선택
    $(".nav").on("focusin", function () {
      $(".type-list").removeClass("on");
    });
    // 분류 리스트
    $(".type-wrap").on("click", function () {
      if ($(this).hasClass("on")) {
        $(this).removeClass("on");
      } else {
        $(this).addClass("on");
      }
    });
    $(".type-wrap").on("mouseleave", function () {
      $(this).removeClass("on");
    });
    $(".type-wrap").each(function () {
      $(this)
        .find("li button")
        .last()
        .on("focusout", function () {
          $(this).parents(".type-wrap").removeClass("on");
        });
    });
    $(".type-list li").on("click", function () {
      var txt = $(this).text();
      $(this).parents(".type-wrap").children("button").text(txt);
      $(".type-list").removeClass("on");
    });

    // map 지역 선택시
    function mapcheck() {
      let idx;
      // let regionLi = $(".region-map li");
      // let regionmap = $(".map");

      // regionLi.on("click focusin", function (e) {
      //   e.preventDefault();
      //   idx = $(this).index();
      //   for (let i = 1; i < regionLi.length + 1; i++) {
      //     regionmap.removeClass("back_bg_" + i);
      //   }
      //   regionmap.toggleClass("back_bg_" + (idx + 1));
      //   regionLi.children("a").removeClass("colorFFF");
      //   $(this).find("a").addClass("colorFFF");
      // });

      // //map focusOut 됐을경우
      // regionLi.on("focusout", function () {
      //   for (let i = 1; i < regionLi.length + 1; i++) {
      //     $(this)
      //       .parents(".map")
      //       .removeClass("back_bg_" + i);
      //   }
      //   regionLi.children("a").removeClass("colorFFF");
      // });

      
      // 이미지맵 js
      $('img[usemap]').rwdImageMaps();
      
      // 폴리곤 좌표 
      $('.region-map area').click( function(){
        idx = $(this).index();
        $('.mapImg').attr('src', '/web/resources/images/layout/map_back_' + ( idx + 1 ) + '.png')
      })

      $('.region-map area').mouseover( function(){
        idx = $(this).index();
        $('.mapImg-over').attr('src', '/web/resources/images/layout/map_img_' + ( idx + 1 ) + '.png')
      })
      $('.region-map area').mouseout( function(){
        idx = $(this).index();
        $('.mapImg-over').attr('src', '/web/resources/images/layout/map_img.png')
      })

    }
    mapcheck();


    // 우리동네 소식 확인하기 슬라이드
    $(function () {
      const contentTSkinA02 = () => {
        $(".contentT-skin_a-02")
          .find(".contentT-slider")
          .each(function () {
            var $this = $(this);
            var contentTSlider = new Swiper($this[0], {
              loop: false,
              slidesPerView: 1,
              spaceBetween: 24,
              speed: 800,
              autoplay: false,
              grabCursor: true,
              watchSlidesProgress: true,
              mousewheelControl: true,
              keyboardControl: true,
              observer: true,
              observeParents: true,
              observeSlideChildren: true,
              navigation: {
                nextEl: ".contentT-skin_a-02 .swiper-next",
                prevEl: ".contentT-skin_a-02 .swiper-prev",
              },
              breakpoints: {
                1024: {
                  slidesPerView: 2,
                },
                1300: {
                  slidesPerView: 3,
                },
              },
              on: {
                init: function (swiper) {
                  var thisSwiper = swiper.$el;
                  $this
                    .parents(".contentT-tab-content")
                    .siblings(".contentT-tab")
                    .find(".nav-link")
                    .on("click", function () {
                      swiper.slideTo(0, 0);
                    });
                },
              },
            });
            $this.find(".swiper-slide").on("focusin", function () {
              contentTSlider.slideTo($(this).parent().index(), 800);
            });
          });
      };
      contentTSkinA02();
    });

    // 말줄임표 표시하고 new아이콘 넣기

    var wordlength = 29;
    $(window).resize(function () {
      var windowWidth = $(window).width();
      if (windowWidth >= 1440) {
        wordlength = 29;
        ellse(wordlength);
      } else if (windowWidth >= 1024) {
        wordlength = 25;
        ellse(wordlength);
      } else if (windowWidth <= 1023 || windowWidth >= 751) {
        wordlength = 50;
        ellse(wordlength);
      } else if (windowWidth <= 750) {
        wordlength = 20;
        ellse(wordlength);
      }
    });

    function ellse(wordlength) {
      $(".slide-title").each(function () {
        length = wordlength; //표시할 글자수 정하기
        $(this).each(function () {
          let txt = $(this).text();
          // $.trim - 앞뒤로 빈 공백을 제거
          let thisTXT = $.trim(txt);
          if (thisTXT.length >= length) {
            $(this).text(thisTXT.substr(0, length) + " ..."); //지정할 글자수 이후 표시할 텍스트
            if ($(this).text().indexOf("...") != -1) {
              $(this)
                .text($(this).text().substr(0, length) + " ...")
                .append(" <i class='new'>"); // 말줄임표 있을경우 i태그 추가
            }
          }
        });
      });
    }
    ellse(wordlength);

    function dataafter() {
      var text = "";
      var el = $(".tab-wrap.right .nav-link.active");
      text = el.data("text");
      el.attr("data-after", text);
    }
    dataafter();

    $(".tab-wrap.right .nav-link").click(function () {
      var a = $(this).data("text");
      $(this).attr("data-after", a);
    });

    var tabSwiper = new Swiper(".tabSwiper", {
      slidesPerView: "auto",
      loop: false,
      speed: 1000,
      spaceBetween: 30,
      slidesPerView: 3,
      grabCursor: true,
      watchSlidesProgress: true,
      mousewheelControl: true,
      keyboardControl: true,
      autoplay: false,
      pagination: {
        el: ".swiper-pagination",
        clickable: true,
      },
      on: {
        progress: function () {
          var swiper = this;
          for (var i = 0; i < swiper.slides.length; i++) {
            var slideProgress = swiper.slides[i].progress;
            var innerOffset = swiper.width * interleaveOffset;
            var innerTranslate = slideProgress * innerOffset;
            /*swiper.slides[i].querySelector(".slide").style.transform =
						"translate3d(" + innerTranslate + "px, 0, 0)";*/
          }
        },
        slideChangeTransitionStart: function (slider) {
          $(".swiper-progress").removeClass("full");
        },
        slideChangeTransitionEnd: function (slider) {
          $(".swiper-progress").addClass("full");
        },
        touchStart: function () {
          var swiper = this;
          for (var i = 0; i < swiper.slides.length; i++) {
            swiper.slides[i].style.transition = "";
          }
        },
      },
    });

    var tabSwiper = new Swiper(".tabSwiper01", {
      slidesPerView: "auto",
      loop: false,
      speed: 1000,
      spaceBetween: 30,
      grabCursor: true,
      watchSlidesProgress: true,
      mousewheelControl: true,
      keyboardControl: true,
      autoplay: false,
      pagination: {
        el: ".swiper-pagination",
        clickable: true,
      },
      on: {
        progress: function () {
          var swiper = this;
          for (var i = 0; i < swiper.slides.length; i++) {
            var slideProgress = swiper.slides[i].progress;
            var innerOffset = swiper.width * interleaveOffset;
            var innerTranslate = slideProgress * innerOffset;
          }
        },
        slideChangeTransitionStart: function (slider) {
          $(".swiper-progress").removeClass("full");
        },
        slideChangeTransitionEnd: function (slider) {
          $(".swiper-progress").addClass("full");
        },
        touchStart: function () {
          var swiper = this;
          for (var i = 0; i < swiper.slides.length; i++) {
            swiper.slides[i].style.transition = "";
          }
        },
      },
    });

    function human() {
      if ($(".humanMember").hasClass("on")) {
        $("body").css({ height: "100%", overflow: "hidden" });
      }

      $(".humanMember-close").click(function () {
        $("#humanMember").removeClass("on");
      });
    }
    human();
  };

  main();
});
