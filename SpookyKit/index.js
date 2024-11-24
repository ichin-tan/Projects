// Adding listener For buttons

for(var i=0; i<document.querySelectorAll("button").length; i++) {
  document.querySelectorAll("button")[i].addEventListener("click", function() {
    var key = this.textContent.toLowerCase();
     voices(key);
  });
}

// Adding listener For keys

document.addEventListener("keypress", function(event) {
    var key = event.key;
     voices(key);
  }
)

// Animation

function animateButton(url1,key) {
  //Change body Background Image
  var body = document.querySelector("body");
  var innerURL = "'" + url1 + "'";
  var outerURL = "url(" + innerURL + ")"
  body.style.backgroundImage = outerURL;

  //Change button background Image
  var currentButton = document.querySelector("." + key);
  currentButton.classList.add("pressed-button");

  setTimeout(function() {
    currentButton.classList.remove("pressed-button");
    var innerURL = "'" + "https://img.freepik.com/free-vector/hand-painted-watercolor-abstract-watercolor-background_23-2149018550.jpg?size=626&ext=jpg&ga=GA1.2.154079655.1676881586&semt=ais" + "'";
    var outerURL = "url(" + innerURL + ")"
    body.style.backgroundImage = outerURL;
  },500);

}

// Sound

function playSound(url) {
  var sound = new Audio(url);
  sound.play();
}

function voices(key) {
  switch(key) {
    case 'a':
      playSound('Sounds/A.mp3');
      animateButton("https://thumbs.dreamstime.com/b/mysterious-ghost-haunted-house-night-mysterious-ghost-haunted-house-101914181.jpg",key);
      break;
    case 'b':
      playSound('Sounds/B.wav');
      animateButton("https://cdn.pixabay.com/photo/2014/11/05/20/43/ghost-518322__340.jpg",key);
      break;
    case 'c':
      playSound('Sounds/C.wav');
      animateButton("https://media.istockphoto.com/id/1327556943/photo/spooky-ghost-moving-alongside-a-hotel-corridor.jpg?b=1&s=170667a&w=0&k=20&c=6oYvOPAhlugQA3QWSOdTqe2mr5ebsq5IDFu6oNYezhc=",key);
      break;
    case 'd':
      playSound('Sounds/D.wav');
      animateButton("https://static.india.com/wp-content/uploads/2015/09/Horror.jpg?impolicy=Medium_Resize&w=1200&h=800",key);
      break;
    case 'e':
      playSound('Sounds/E.wav');
      animateButton("https://s26162.pcdn.co/wp-content/uploads/2019/11/Momo.jpg",key);
      break;
    case 'f':
      playSound('Sounds/F.wav');
      animateButton("https://media.istockphoto.com/id/545359498/photo/terrible-dead-ghost-woman-in-the-water.jpg?s=612x612&w=0&k=20&c=PCkTsYCLZsFGyj3FLrA7jLpXIw6-aJUgsPGLVWtVCZc=",key);
      break;
    case 'g':
      playSound('Sounds/G.wav');
      animateButton("https://t3.ftcdn.net/jpg/03/83/19/86/360_F_383198638_tkPjQTb4WiZ5gbwyrRYsCeaDDNBLzPni.jpg",key);
      break;
    case 'h':
      playSound('Sounds/H.wav');
      animateButton("https://s1.ticketm.net/dam/a/0bb/82054d6b-8fa6-4395-a49a-ae90bc4d60bb_SOURCE",key);
      break;
    case 'i':
      playSound('Sounds/I.wav');
      animateButton("https://images.unsplash.com/photo-1603516863860-7d5c93a83fe8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Z2hvc3R8ZW58MHx8MHx8&w=1000&q=80",key);
      break;
    case 'j':
      playSound('Sounds/J.wav');
      animateButton("https://mlpnk72yciwc.i.optimole.com/cqhiHLc.IIZS~2ef73/w:auto/h:auto/q:75/https://bleedingcool.com/wp-content/uploads/2020/07/Sadako-vs-Kayako.jpg",key);
      break;
    case 'k':
      playSound('Sounds/K.wav');
      animateButton("https://staticimg.amarujala.com/assets/images/2017/03/18/750x506/_1489823532.jpeg",key);
      break;
    case 'l':
      playSound('Sounds/L.wav');
      animateButton("https://i.pinimg.com/736x/da/0c/7b/da0c7b74f1ddff701f193b0ffe63d1ac--real-ghost-pictures-ghost-images.jpg",key);
      break;
    case 'm':
      playSound('Sounds/M.wav');
      animateButton("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVOQGnSEOhMJW3j3CFiBeZLwg3yayL5Djuyg&usqp=CAU",key);
      break;
    case 'n':
      playSound('Sounds/N.wav');
      animateButton("https://images.indianexpress.com/2016/05/delhi-girl-main.jpg?w=389",key);
      break;
    case 'o':
      playSound('Sounds/O.wav');
      animateButton("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5092vz_85HLY6iFMEB7fMdzw23lBrdfK7ZQ&usqp=CAU",key);
      break;
    case 'p':
      playSound('Sounds/P.wav');
      animateButton("https://akm-img-a-in.tosshub.com/indiatoday/images/story/201509/ghost---storysize_647_092315101820.jpg?VersionId=kehMqSjDY428k901gjM8g_TeTbJoAqOR&size=690:388",key);
      break;
    case 'q':
      playSound('Sounds/Q.wav');
      animateButton("https://media.gettyimages.com/id/108328411/photo/ghost-woman-on-haunted-staircase.jpg?s=612x612&w=gi&k=20&c=jgaGSwzwq39gVcIhFx62vwcirEVe0DmFPsNLqaDYfgw=",key);
      break;
    case 'r':
      playSound('Sounds/R.mp3');
      animateButton("https://media.tenor.com/yhAteqmTQ_UAAAAC/ghost-horror.gif",key);
      break;
    case 's':
      playSound('Sounds/S.wav');
      animateButton("https://media.tenor.com/uw5s-aHlviAAAAAM/scary-ghost.gif",key);
      break;
    case 't':
      playSound('Sounds/T.wav');
      animateButton("https://i.gifer.com/kgY.gif",key);
      break;
    case 'u':
      playSound('Sounds/U.wav');
      animateButton("https://media.tenor.com/SZdM3TiDsm8AAAAM/possessed-demon-girl.gif",key);
      break;
    case 'v':
      playSound('Sounds/V.wav');
      animateButton("https://media.tenor.com/PmvzH0RPFggAAAAM/ghost-supernatural.gif",key);
      break;
    case 'w':
      playSound('Sounds/W.wav');
      animateButton("https://media.tenor.com/3K99Jipq9dUAAAAM/ghost-creepy.gif",key);
      break;
    case 'x':
      playSound('Sounds/X.wav');
      animateButton("https://gifdb.com/images/high/scary-angry-ghost-oq35lqhgs1irlh10.gif",key);
      break;
    case 'y':
      playSound('Sounds/Y.mp3');
      animateButton("https://media.tenor.com/IrACH87ZpPUAAAAM/bhoot-ghost.gif",key);
      break;
    case 'z':
        playSound('Sounds/Z.wav');
        animateButton("https://gifdb.com/images/high/scary-mumbling-ghost-c42ncebjyhpszw5t.gif",key);
        break;
    default:
      break;
}
}
