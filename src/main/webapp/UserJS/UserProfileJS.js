//            sort js for products with category list
function sortProducts(category) {
    const categoryCards = document.getElementsByClassName('category-card');
    for (let i = 0; i < categoryCards.length; i++) {
        const card = categoryCards[i];
        const cardCategory = card.getAttribute('data-category');
        if (cardCategory === category) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    }
}

// Get the sign up modal
var signupModal = document.getElementById('signupModal');
// When the user clicks anywhere outside of the sign up modal, close it
window.onclick = function (event) {
    if (event.target == signupModal) {
        signupModal.style.display = "none";
    }
};

// Get the login modal
var loginModal = document.getElementById('loginModal');
// When the user clicks anywhere outside of the login modal, close it
window.onclick = function (event) {
    if (event.target == loginModal) {
        loginModal.style.display = "none";
    }
};

// Get the cart modal
var cartModal = document.getElementById('cartModal');
// When the user clicks anywhere outside of the login modal, close it
window.onclick = function (event) {
    if (event.target == cartModal) {
        cartModal.style.display = "none";
    }
};

//            update price on quantity click
function updatePrice(input, productId, initialPrice) {
    var quantity = parseInt(input.value);
    var newPrice = quantity * initialPrice;
    document.getElementById("price_" + productId).textContent = newPrice.toFixed(2);

    // Recalculate the total price
    var total = 0;
    var prices = document.querySelectorAll("[id^=price_]");
    prices.forEach(function (price) {
        total += parseFloat(price.textContent);
    });
    document.getElementById("totalPrice").textContent = total.toFixed(2);
    document.getElementById("payNowPrice").textContent = total.toFixed(2);
}