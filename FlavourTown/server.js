const express = require("express")
const app = express()
const path = require('path');
const port = process.env.PORT || 8080

// tells express to use EJS
app.set("view engine", "ejs")

// - extract data sent by <form> element in the client
app.use(express.urlencoded({extended:true}))

// Serve static files from the root directory (where styles.css is located)
app.use(express.static(path.join(__dirname)));

app.use(express.json());

// import database
const mongoose = require("mongoose");
require("dotenv").config();

// Menu Item
const MenuItem = require('./models/menuItem');

// Routes
const restaurantRoutes = require('./routes/restaurant');
app.use('/restaurant', restaurantRoutes);

app.get("/", async (req,res) => {
    try {
        const menuItems = await MenuItem.find();
        return res.render("home.ejs", {arrMenuItems: menuItems})
    } catch (error) {
        return res.render("home.ejs")
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