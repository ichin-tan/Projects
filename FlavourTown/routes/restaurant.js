const express = require('express');
const router = express.Router();
const MenuItem = require('../models/menuItem');
const Order = require('../models/order');
const mongoose = require("mongoose");

// Place Order - Form - UI
router.get('/placeOrder', async (req, res) => {
    try {
        const menuItems = await MenuItem.find();
        return res.render("place-order.ejs", { arrMenuItems: menuItems })
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
});

// Order placed - To receive value from form - POST method
router.post('/placeOrder', async (req, res) => {

    console.log(req.body);

    if (req.body.menuItemId === "") {
        return res.render("error-message.ejs", { errorMessage: "Please select an item!" })
    }

    if (req.body.customerName === "") {
        return res.render("error-message.ejs", { errorMessage: "Please enter customer name!" })
    }

    if (req.body.contactNo === "") {
        return res.render("error-message.ejs", { errorMessage: "Please enter contact number!" })
    }

    if (req.body.deliveryAddress === "") {
        return res.render("error-message.ejs", { errorMessage: "Please enter delivery address!" })
    }

    try {
        const menuItem = await MenuItem.findById(req.body.menuItemId);
        if (!menuItem) {
            return res.render("error-message.ejs", { errorMessage: "Menu Item not found!" })
        }
        const newOrder = new Order({
            customerName: req.body.customerName,
            deliveryAddress: req.body.deliveryAddress,
            menuItemId: menuItem._id,
            orderPrice: (menuItem.price * 1.13).toFixed(2),
            status: req.body.status,
            contactNo: req.body.contactNo
        });

        if (req.body.orderDate !== "") {
            const selectedDate = new Date(req.body.orderDate);
            const utcDate = selectedDate.toISOString();
            console.log(utcDate);
            newOrder.orderDate = utcDate
        }        
        await newOrder.save();
        return res.render("order-summary.ejs", { order: newOrder, menuItemPrice: menuItem.price })
    } catch (err) {
        return res.status(400).json({ message: err.message });
    }
});

// Check Status of Order - Form - UI
router.get('/orderStatus', (req, res) => {
    res.render("check-status.ejs")
});

// Get order status by id 
router.post('/orderStatus', async (req, res) => {
    try {
        if (mongoose.Types.ObjectId.isValid(req.body.orderId) === false) {
            return res.render("error-message.ejs", { errorMessage: "Invalid order ID format" })
        }
        const order = await Order.findById(req.body.orderId);
        if (order) {
            const populatedOrder = await Order.findById(order._id).populate('menuItemId');
            return res.render("order-summary.ejs", { order: order, menuItemPrice: populatedOrder.menuItemId.price })
        } else {
            return res.render("error-message.ejs", { errorMessage: "Order not found!" })
        }
    } catch (error) {
        return res.status(500).json({ message: error.message });
    }
});

module.exports = router;
