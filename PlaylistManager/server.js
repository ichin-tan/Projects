const express = require("express")
const app = express()
const mongoose = require("mongoose");
const path = require('path');
const port = process.env.PORT || 3000

// tells express to use EJS
app.set("view engine", "ejs")

app.use(express.urlencoded({extended:true}))

// Serve static files from the root directory (where styles.css is located)
app.use(express.static(path.join(__dirname)));

app.use(express.json());

require("dotenv").config();

// Import song model
const {Song, PlaylistSong} = require('./models/song');

app.get("/", async (req,res) => {
    try {
        const arrSongs = await Song.find()
        const arrPlaylistSongs = await PlaylistSong.find()
        return res.render("home.ejs", {arrSongs: arrSongs, arrPlaylistSongs: arrPlaylistSongs})
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
})

app.post("/addSong", async (req,res) => {
    console.log(req.body);

    const existedSong = await Song.find({title: req.body.title, artist: req.body.artist})            
    if(existedSong.length === 1) {
        console.log("Song already exist in database!");
        return res.redirect("/")
    }
    console.log("Not working");
    
    const newSong = new Song({
        title: req.body.title,
        artist: req.body.artist,
        image: req.body.image
    })
    console.log(newSong);
    try {
        await newSong.save()
        return res.redirect("/");
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
})

app.post("/addToPlaylist/:id", async (req,res) => {
    try {
        const song = await Song.findById(req.params.id)
        if(song) {
            const existedSong = await PlaylistSong.find({title: song.title, artist: song.artist})            
            if(existedSong.length === 1) {
                console.log("Song already exist in playlist!");
                return res.redirect("/")
            }
            const newPlaylistSong = new PlaylistSong({
                title: song.title,
                artist: song.artist,
                image: song.image
            }) 
            await newPlaylistSong.save()
            return res.redirect("/");
        } else {
            return res.status(404).json({ message: "Couldnt find the song in database!" });
        }
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
})

app.post("/removeFromPlaylist/:id", async (req,res) => {
    try {
        await PlaylistSong.findByIdAndDelete(req.params.id)
        return res.redirect("/")
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
})

const startServer = async () => {   
   console.log(`The server is running on http://localhost:${port}`)
   console.log(`Press CTRL + C to exit`)

   // MongoDB Connection
   try {
        await mongoose.connect(process.env.MONGODB_URI)
        console.log("Success! Connected to MongoDB")
    } catch (err) {
        console.error("Error connecting to MongoDB:", err);
    }  

}
app.listen(port, startServer)