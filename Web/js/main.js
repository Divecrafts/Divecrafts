// If you don't want the particles, change the following to false:
const doParticles = true;




// Do not mess with the rest of this file unless you know what you're doing :P

function getWidth() { // credit to travis on stack overflow
  return Math.max(
    document.body.scrollWidth,
    document.documentElement.scrollWidth,
    document.body.offsetWidth,
    document.documentElement.offsetWidth,
    document.documentElement.clientWidth
  );
}

if (doParticles) {
	if (getWidth() < 400) $.firefly({minPixel: 1,maxPixel: 2,total: 20});
	else $.firefly({minPixel: 1,maxPixel: 3,total: 40});
}

// This is for the click to copy
let t;
$(document).ready(()=>{
	t = $(".ip").html();
  if (isMobile()){
    $("video").remove();
    $(".control").remove();
  }
})

function isMobile(){
  var isMobile = (/iphone|ipod|android|ie|blackberry|fennec/).test
     (navigator.userAgent.toLowerCase());
  return isMobile;
}

$(".control").click(() => {
  const video = document.querySelector("video");

  document.querySelector(".control img").src = video.muted ?
   'https://i.imgur.com/2wTjPZn.png' : 'https://i.imgur.com/QmSJVkp.png';

  video.muted = !video.muted;
  video.volume = 0.1;
});

$(document).on("click",".ip",()=>{
	let copy = document.createElement("textarea");
	copy.style.position = "absolute";
	copy.style.left = "-99999px";
	copy.style.top = "0";
	copy.setAttribute("id", "ta");
	document.body.appendChild(copy);
	copy.textContent = t;
	copy.select();
	document.execCommand("copy");
	$(".ip").html("<span class='extrapad'>IP copied!</span>");
	setTimeout(function(){
		$(".ip").html(t);
		var copy = document.getElementById("ta");
		copy.parentNode.removeChild(copy);
	},800);
});

// This is to fetch the player count
$(document).ready(()=>{
  const ip = $(".sip").attr("data-ip");
  const port = $(".sip").attr("data-port");

  $.get(`https://mcapi.us/server/status?ip=${ip}&port=${port}`, (result)=>{
    if (result.online) {
      $(".sip").html(result.players.now);
    } else {
      $(".playercount").html("Server isn't online!");
    }
  });

  setInterval(()=>{
    $.get(`https://mcapi.us/server/status?ip=${ip}&port=${port}`, (result)=>{
      if (result.online) {
        $(".sip").html(result.players.now);
      } else {
        $(".playercount").html("Server isn't online!");
      }
    });
  }, 3000);
});
