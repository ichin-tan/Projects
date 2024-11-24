
// Payment - form code

const paymentDetails = {
    passType : "", 
    numberOfTickets : 1, 
    name : "", 
    email : "", 
    contactNo : "", 
    address : "", 
    cardType : "",
    cardNumber : "", 
    expiryDate : "", 
    cvv : ""
}

const divPaymentForm = document.querySelector(".payment-form");
divPaymentForm.style.display = "";

const btnPurchase = document.querySelector(".btn-purchase");

let validationMessage = document.querySelector("#validation-message");
validationMessage.style.display = "none";

const divOrderSummary = document.querySelector(".order-summary");
divOrderSummary.style.display = "none";

const successMessage = document.querySelector("#success-order-p");
successMessage.style.display = "none"

btnPurchase.addEventListener("click", () => {
    paymentDetails.passType = document.querySelector("#pass-type").value;
    paymentDetails.numberOfTickets = document.querySelector("#number-of-tickeit-field").value;
    paymentDetails.name = document.querySelector("#name-field").value;
    paymentDetails.email = document.querySelector("#email-field").value;
    paymentDetails.contactNo = document.querySelector("#contact-field").value;
    paymentDetails.address = document.querySelector("#address-field").value;
    paymentDetails.cardType = document.querySelector("#card_type").value;
    paymentDetails.cardNumber = document.querySelector("#card-number-field").value;
    paymentDetails.expiryDate = document.querySelector("#expiration-date-field").value
    paymentDetails.cvv = document.querySelector("#cvv-field").value

    console.log(paymentDetails); 

    // Check empty validation
    let errorMessage = ""

    if(paymentDetails.passType == "") {
        errorMessage = "ERROR! Please select pass type." 
    } else if(paymentDetails.numberOfTickets === "") {
        errorMessage = "ERROR! Please enter number of tickets."        
    } else if (paymentDetails.name === "") {
        errorMessage = "ERROR! Please enter name."
    } else if (paymentDetails.email === "") {
        errorMessage = "ERROR! Please enter email."
    } else if (paymentDetails.contactNo === "") {
        errorMessage = "ERROR! Please enter contact number."
    } else if (paymentDetails.contactNo.length !== 10) {
        errorMessage = "ERROR! Please enter a valid contact number which is 10 digits long."
    } else if (paymentDetails.address === "") {
        errorMessage = "ERROR! Please enter address."
    } else if (paymentDetails.cardType === "") {
        errorMessage = "ERROR! Please select card type."
    } else if (paymentDetails.cardNumber === "") {
        errorMessage = "ERROR! Please enter card number."
    } else if (paymentDetails.cardNumber.length !== 6) {
        errorMessage = "ERROR! Please enter a valid card number which is 6 digits long."
    } else if (paymentDetails.expiryDate === "") {
        errorMessage = "ERROR! Please enter expiry date."
    } else if (paymentDetails.cvv === "") {
        errorMessage = "ERROR! Please enter CVV."
    } else if (paymentDetails.cvv.length !== 3) {
        errorMessage = "ERROR! Please enter a valid cvv which is 3 digits long."
    }

    if (errorMessage === "") {
        validationMessage.style.display = "none";
        divOrderSummary.style.display = ""
        divPaymentForm.style.display = "none";

        // Code for order summary
        const numberOfTicketsP = document.querySelector("#number-of-tickets-summary");
        const pricePerTicketP = document.querySelector("#price-per-ticket-summary");
        const totalPriceP = document.querySelector("#total-price-summary");
        const taxP = document.querySelector("#tax-summary");
        const finalPriceP = document.querySelector("#final-price-summary");

        numberOfTicketsP.innerText = `Number of tickets - ${paymentDetails.numberOfTickets}`;

        const pricePerTicket = paymentDetails.passType === "basic" ? 79 : 149;
        pricePerTicketP.innerText = `Price per ticket - $${pricePerTicket.toFixed(2)}`;

        const totalPrice = parseInt(paymentDetails.numberOfTickets) * pricePerTicket;
        totalPriceP.innerText = `Total price - $${totalPrice.toFixed(2)}`;

        const tax = (totalPrice * 0.13).toFixed(2);
        taxP.innerText = `Tax (13%) - $${tax}`;

        const finalPrice = (totalPrice * 1.13).toFixed(2);
        finalPriceP.innerText = `Final price - $${finalPrice}`;
    } else {
        validationMessage.style.display = "";
        validationMessage.innerText = errorMessage
    }
})

//validation for only entering number

document.querySelector("#number-of-tickeit-field").addEventListener("keypress", (evt)=> {
    evt.returnValue = (evt.keyCode >= 48 && evt.keyCode <= 57);
})
document.querySelector("#contact-field").addEventListener("keypress", (evt)=> {
    evt.returnValue = (evt.keyCode >= 48 && evt.keyCode <= 57);
})
document.querySelector("#card-number-field").addEventListener("keypress", (evt)=> {
    evt.returnValue = (evt.keyCode >= 48 && evt.keyCode <= 57);
})
document.querySelector("#cvv-field").addEventListener("keypress", (evt)=> {
    evt.returnValue = (evt.keyCode >= 48 && evt.keyCode <= 57);
})

// Edit order

const btnEditOrder = document.querySelector(".btn-edit-order");
btnEditOrder.addEventListener("click", () => {
    divPaymentForm.style.display = "";
    divOrderSummary.style.display = "none"
})

// Confirm order

const btnConfirmOrder = document.querySelector(".btn-confirm-order");
btnConfirmOrder.addEventListener("click", ()=> {
    divPaymentForm.style.display = "none";
    const divOrderDetail = document.querySelector(".order-details");
    divOrderDetail.style.display = "none";
    
    successMessage.style.display = "";
    successMessage.innerText = "You have successfully placed your order. Tickets will be delievered via mail in next 2 business days.";
})
