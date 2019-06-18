function changeForm(form) {
	$(document).find('.main').addClass('animated bounceOutUp').one('webkitAnimationEnd', function(){
		$(document).find('.main').hide();
		$(document).find('.main').removeClass('animated bounceOutUp');

		if (form == "login") {
			$(document).find(".main").css({ "height" : "390px" });
			$(".form-login").show();
			$(".form-cadastro").hide();
			$(".form-mudarsenha").hide();
		} else if (form == "cadastro") {
			$(document).find(".main").css({ "height" : "460px" });
			$(".form-login").hide();
			$(".form-cadastro").show();
			$(".form-mudarsenha").hide();
		} if (form == "mudarsenha") {
			$(document).find(".main").css({ "height" : "360px" });
			$(".form-login").hide();
			$(".form-cadastro").hide();
			$(".form-mudarsenha").show();
		}

		$(document).find('.main').ready(function() {
			$(document).find('.main').show();
			$(document).find('.main').addClass('animated bounceInDown').one('webkitAnimationEnd', function() {
				$(document).find('.main').removeClass('animated bounceInDown');
			});
		});
	});
}