const btnCalculate = document.querySelector("button");
const receiptArea = document.querySelector(".bill-receipt");

receiptArea.style.display = "none";

const calculateButtonTapped = () => {

    // Getting value from inputs
    const onPeakHours = document.querySelector("#input-on-peak-hours").value;
    const offPeakHours = document.querySelector("#input-off-peak-hours").value;
    const province = document.querySelector("#input-province").value;

    // Convert the values into numbers
    const onPeakNumber = parseFloat(onPeakHours);
    const offPeakNumber = parseFloat(offPeakHours);

    // Create object of bill
    const bill = {
        provincialRebate : 0
    }

    // Add the properties to bill object
    bill.onPeakHours = onPeakNumber;
    bill.offPeakHours = offPeakNumber;
    bill.onPeakPrice = parseFloat((bill.onPeakHours * 0.132).toFixed(2));
    bill.offPeakPrice = parseFloat((bill.offPeakHours * 0.065).toFixed(2));    
    bill.grossConsumptionCharges = parseFloat((bill.onPeakPrice + bill.offPeakPrice).toFixed(2));
    bill.hst = parseFloat((bill.grossConsumptionCharges * 0.13).toFixed(2));    

    if (province === "British Columbia") {
        bill.provincialRebate = parseFloat((bill.grossConsumptionCharges * 0.08).toFixed(2));  
    }
    bill.totalPay = parseFloat((bill.grossConsumptionCharges + bill.hst - bill.provincialRebate).toFixed(2));

    console.log(bill);

    // Displaying values where necessary
    const onPeakPriceLabel = document.querySelector("#peak-usage-box-1-price");
    onPeakPriceLabel.innerText = `$${bill.onPeakPrice}`;

    const onPeakPriceDesLabel = document.querySelector("#peak-usage-box-1-des");
    onPeakPriceDesLabel.innerText = `${bill.onPeakHours} kwH @ $0.132/hr`;
    
    const offPeakPriceLabel = document.querySelector("#peak-usage-box-2-price");
    offPeakPriceLabel.innerText = `$${bill.offPeakPrice}`;

    const offPeakPriceDesLabel = document.querySelector("#peak-usage-box-2-des");
    offPeakPriceDesLabel.innerText = `${bill.offPeakHours} kwH @ $0.065/hr`;

    const totalConsumptionChargesLabel = document.querySelector("#total-consumption-charge-label");
    totalConsumptionChargesLabel.innerText = `Total Consumption Charges: $${bill.grossConsumptionCharges}`;

    const hstLabel = document.querySelector("#sales-tax-label");
    hstLabel.innerText = `Sales Tax (13%): $${bill.hst}`;

    const provincialRebateLabel = document.querySelector("#provincial-rebate-label");
    provincialRebateLabel.innerText = `Provincial Rebate: -$${bill.provincialRebate}`;

    const totalPriceLabel = document.querySelector("#total-price-label");
    totalPriceLabel.innerText = `$${bill.totalPay}`;

    // show the bill area to the user
    receiptArea.style.display = "";
}

btnCalculate.addEventListener("click", calculateButtonTapped);
