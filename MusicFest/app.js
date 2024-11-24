
let arrArtists = [
    {
        name: "Ed Sheeran", 
        imageURL: "ed.jpg", 
        info: "With a guitar in hand and a heart full of stories, Ed Sheeran has become one of the world’s most beloved singer-songwriters. Known for his soulful lyrics, captivating melodies, and ability to connect with fans on a deeply personal level, Ed’s music transcends genres and generations.", 
        time: "8:00-9:00 pm",
        genre: "Pop"
    }, 
    {
        name: "Taylor Swift", 
        imageURL: "taylor.jpg", 
        info: "Taylor Swift, a global sensation and storyteller at heart, has redefined the music and scape with her evocative lyrics and genre-blending sound. From the heartfelt ballads of 'Love Story' to the raw emotion of 'All Too Well', her music captures the intricacies of love, heartbreak and self-discovery.", 
        time: "9:30-11:00 pm",
        genre: "Pop"
    },
    {
        name: "One Direction", 
        imageURL: "oned.jpg", 
        info: "One Direction took the world by storm with their infectious energy and undeniable charm, leaving an indelible mark on pop music. From their breakout debut with 'What Makes You Beautiful' to the heartfelt anthems of 'Little Things' and 'Story of My Life', their music became the soundtrack to countless lives.", 
        time: "11:30-1:00 pm",
        genre: "Pop"
    },
]

document.addEventListener("DOMContentLoaded", () => {
    arrArtists.siz
    for (let artist of arrArtists) {
        document.querySelector(".artist-main").innerHTML +=
    `
        <div class="artist-box">
            <div>
                <img src=${artist.imageURL} alt="" class="artist-image">
            </div>
            <div class="artist-info">
                <p class="artist-name"> ${artist.name} </p>
                <p>${artist.info}</p>
                <p>Time: ${artist.time}</p>
                <p>Genre: ${artist.genre}</p>
            </div>
        </div>
    `
    }

})