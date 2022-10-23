$(function () {
  const sub = () => {
    // bySector 갯수별 width값
    function liLength() {
      var item1 = $(".bySector-item");
      var countItem1 = "";
      countItem1 = item1.length;
      min1 = Math.floor(100 / countItem1);
      item1.css({ width: min1 + "%" });
    }
    liLength();

    // 스마트검색
    $(".smart-title").click(function () {
      $(".smart-title").toggleClass("on");
      $(".smart-category").toggleClass("on");
    });

    var swiper = new Swiper(".inp-swiper", {
      slidesPerView: "auto",
      spaceBetween: 0,
      breakpoints:{
        500: {
          slidesPerView: 5,
        },
        750: {
          slidesPerView: 5,
        },
        1024: {
          slidesPerView: 7,
          spaceBetween: 0,
        },
      }
    });

    // 강좌검색 탭스와이퍼
    

    var swiper = new Swiper(".tab-swiper", {
      slidesPerView: 3,
      spaceBetween: 0,
      grabCursor: true,
      watchSlidesProgress: true,
      mousewheelControl: false,
      keyboardControl: true,
      observer: true,
      observeParents: true,
      observeSlideChildren: true,
      breakpoints:{
        500: {
          slidesPerView: 5,
        },
        1024: {
          slidesPerView: 6,
        },
      },
      
    });



    // 서치바 리스트
    $(".searchBar-item").on("click", function (e) {
      var $this = $(this);
      var serachList = $this.find("ul");
      if (!$this.hasClass("on")) {
        $this.siblings().removeClass("on");
        $this.siblings().find("ul").removeClass("on");
        $this.addClass("on");
      } else {
        $this.removeClass("on");
      }
      serachList.toggleClass("on");
    });

    $(".searchBar-item").on("mouseleave", function () {
      var barlist = $(".searchBar-list");
      if (barlist.is(":visible")) {
        setRemove(barlist);
        barlist.removeClass("on");
      }
    });

    $(".searchBar-item").each(function () {
      $(this)
        .find("li")
        .last()
        .on("focusout", function () {
          $(this).parents(".searchBar-item").removeClass("on");
          $(this).parents(".searchBar-list").removeClass("on");
        });
    });
    $(".searchBar-item li").on("click", function (e) {
      var txt = $(this).text();
      var $parentsItem = $(this).parents(".searchBar-item");
      $parentsItem.children("button").text(txt);
      $parentsItem.addClass("on");
      $parentsItem.addClass("on");
    });

    function setRemove(x) {
      x.parents().removeClass("on");
    }
    // //서치바 리스트

    //분류 나누기
    $(".article-select").click(function () {
      $(this).toggleClass("on");
    });

    $(".article-select li").on("click", function () {
      var txt = $(this).text();
      $(this).parents(".article-select").children("button").text(txt);
    });

    $(".article-select li")
      .find("button")
      .last()
      .on("focusout", function () {
        $(".article-select").toggleClass("on");
      });

    $(".article-select").on("mouseleave", function () {
      var barlist = $(".select-list");
      if (barlist.is(":visible")) {
        $(".article-select").removeClass("on");
      }
    });

    // Datapiker
    function picker() {
      var picker = $(".datepicker");
      if (picker.hasClass("birth")) {
        $(".datepicker.birth").datepicker({
          autoclose: true,
          format: "yyyymmdd",
        });
      } else {
        $(".datepicker").datepicker({
          autoclose: true,
        });
      }
    }
    picker();

    // $(".datepicker.birth").datepicker({
    //   format: 'yyyymmdd',
    // });

    //파일 업로드
    $("#inp02_5_2").click(function () {
      $("#inp02_5_3").click();
    });

    // faq
    $(".faq-btn").click(function () {
      $(this).parents(".faq-item").toggleClass("on");
      $(this).siblings("div").stop().slideToggle();
    });

    // 약관동의
    // 전체 클릭
    $(".re-agree")
      .find(".inpChk")
      .on("click", function () {
        if ($(this).is(":checked")) {
          $(".input-group").find(".inp").prop("checked", true);
        } else {
          $(".input-group").find(".inp").prop("checked", false);
        }
      });
    // 체크박스 개별 선택
    $(".inp").on("click", function () {
      var is_checked = true;

      $(".inp").each(function () {
        is_checked = is_checked && $(this).is(":checked");
      });

      $(".inpChk").prop("checked", is_checked);
    });

    var $width750 = window.innerWidth;
    function tableResize() {
      if ($width750 <= 750) {
        var $th = $(".multiple thead th");
        var $thLength = $th.length;
        var $thWidth = 100 / $thLength;

        $th.css({ width: $thWidth + "%" });
      }
    }
    tableResize();

    $(window).resize(function () {
      tableResize();
    });
  };
  sub();
});
