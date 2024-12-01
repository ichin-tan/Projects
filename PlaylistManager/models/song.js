const mongoose = require('mongoose');

const songSchema = new mongoose.Schema({
    title: String,
    artist: String,
    image: String
});

const Song = mongoose.model('Song', songSchema);
const PlaylistSong = mongoose.model('PlaylistSong', songSchema);

module.exports = {Song, PlaylistSong}
