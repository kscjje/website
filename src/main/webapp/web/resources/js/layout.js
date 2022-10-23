$(function () {
  const layout = () => {
    // 알람
    $(".alarm").click(function () {
      $(".preview").addClass("on");
    });
    $(".preview-close").click(function () {
      $(".preview").removeClass("on");
    });

    // 검색카테고리 선택
    $(".search-list > button").click(function () {
      $(".search-list").toggleClass("on");
    });
    $(".item-selec li").mouseenter(function () {
      $(".gnb").css({ "pointer-events": "none" });
    });
    $(".search-item").mouseleave(function () {
      $(".search-list").removeClass("on");
    });
    $(".search-item")
      .find("li button")
      .last()
      .on("focusout", function () {
        $(".search-list").removeClass("on");
        $(".gnb").css({ "pointer-events": "auto" });
      });

    $(".search-item li").click(function (e) {
      e.preventDefault();
      var txt = $(this).text();
      var buttonTxt = $(".search-list").children("button");
      buttonTxt.text(txt);
      $(".search-item").toggleClass("on");
      $(".search-list").toggleClass("on");
      $(".gnb").css({ "pointer-events": "auto" });
    });

    // gnb포커스 인/아웃
    $(".gnb-item a").focus(function () {
      gnbadd();
      $(".dim-5").addClass("on");
    });
    $(".sitemap-item")
      .find("a")
      .last()
      .focusout(function () {
        gnbremove();
      });

    // gnb 마우스 오버
    $(".gnb").mouseover(function () {
      gnbadd();
    });
    $(".gnb").mouseleave(function () {
      gnbremove();
    });

    function gnbadd() {
      $(".gnb").addClass("on");
      $(".dim-5").addClass("on");
    }
    function gnbremove() {
      $(".gnb").removeClass("on");
      $(".dim-5").removeClass("on");
      $(".item-list.area").removeClass("on");
      $(".more").removeClass("on");
      $(".addition").removeClass("on");
    }

    // 메뉴 더보기
    $(".sitemap-item .addition").click(function (e) {
      e.preventDefault();
      $(this).toggleClass("on");
      $(this).find(".more").toggleClass("on");
      $(this).siblings(".area").toggleClass("on");
      // $(".all-menu").css({ "padding-bottom": "80px" });
    });

    $(".item-list.area")
      .find("li")
      .click(function () {
        var $this = $(this);
        var $areatxt = $this.text();
        var $addtion = $(".addition");
        $this.parent(".area").removeClass("on");
        $addtion
          .text($areatxt)
          .append("<i class='more on'></i>")
          .removeClass("on");
        $addtion.find(".more").removeClass("on");
      });
    $(".item-list.area").mouseleave(function () {
      var $this = $(this);
      var $addtion = $(".addition");
      $this.removeClass("on");
      $addtion.removeClass("on").find(".more").removeClass("on");
    });

    // 푸터 하단 related
    $(".related-title a").on("click", function (e) {
      e.preventDefault();
      $(this)
        .closest(".related-group")
        .toggleClass("active")
        .siblings()
        .removeClass("active");
    });

    function labelChk() {
      window.addEventListener("keydown", function (e) {
        var focusEl = document.activeElement;
        if (e.keyCode == 13) {
          $("label:focus").click();
        }
      });
    }
    labelChk();

    // 모바일 메뉴
    $(".btn-menu").click(function () {
      $("#gnb-m").addClass("on");
      $(".dim-10").addClass("on");
      $("body").addClass("mobile");
    });
    $(".close").click(function () {
      $("#gnb-m").removeClass("on");
      $(".dim-10").removeClass("on");
      $("body").removeClass("mobile");
    });
    $(".dim").click(function () {
      $("#gnb-m").removeClass("on");
      $(this).removeClass("on");
      $("body").removeClass("mobile");
    });
    $("#gnb-m .body .btn-dep1").click(function () {
      if (!$(this).parent().hasClass("active")) {
        $(this).parent().addClass("active").siblings().removeClass("active");
        $(this).parent().siblings().children(".dep2").hide();
        $(this).siblings(".dep2").stop().slideDown();
      }
    });

    $("#gnb-m .body ul button").on("click", function () {
      if ($(this).hasClass("on")) {
        $(this).removeClass("on");
        $(this).siblings("ul").removeClass("on");
      } else {
        $(this).addClass("on");
        $(this).siblings("ul").addClass("on");
      }
    });

    $(".btn-dep3").on("click", function () {
      var txt = $(this).text();
      $(this).parents(".dep2").find("button").text(txt);
      $("#gnb-m .body ul button").siblings("ul").removeClass("on");
      $("#gnb-m .body ul button").removeClass("on");
    });
  };
  layout();
});
