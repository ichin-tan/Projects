const mongoose = require('mongoose');

const orderSchema = new mongoose.Schema({
    customerName: {
        type: String,
        required: true
    },
    deliveryAddress: {
        type: String,
        required: true
    },
    menuItemId: {
        type: mongoose.Schema.Types.ObjectId, // Single reference to a MenuItem
        ref: 'MenuItem',
        required: true
    },
    orderPrice: {
        type: Number,
        required: true
    },
    orderDate: {
        type: Date,
        default: Date.now
    },
    status: {
        type: String,
        enum: ['READY FOR DELIVERY', 'IN TRANSIT', 'DELIVERED'],
        default: 'READY FOR DELIVERY'
    },
    contactNo: {
        type: String,
        required: true
    }
}, { timestamps: true });

module.exports = mongoose.model('Order', orderSchema);