const mongoose = require('mongoose');

const menuItemSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    image: String,
    description: String,
    price: {
        type: Number,
        required: true
    }
}, { timestamps: true });

module.exports = mongoose.model('MenuItem', menuItemSchema);
