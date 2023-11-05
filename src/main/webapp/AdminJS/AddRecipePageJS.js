window.addEventListener('load', function () {
    document.documentElement.style.zoom = '90%';
});

// JavaScript array to store ingredients
var ingredientsArray = [];

// Get element by ID helper function
function getElement(id) {
    return document.getElementById(id);
}

// Add event listener to save ingredient and close popup form
getElement('saveIngredientBtn').addEventListener('click', function () {
    /////////////inner text
    var ingredientDrop = getElement('ingredientDropdown');
    var ingredientSelected = ingredientDrop.options[ingredientDrop.selectedIndex];
    var ingredient = ingredientSelected.innerText;
    var ingredientID = getElement('ingredientDropdown').value;
    //getting the inner text
    var unitDrop = getElement('unitDropdown');
    var unitSelected = unitDrop.options[unitDrop.selectedIndex];
    var unit = unitSelected.innerText;
    var unitID = getElement('unitDropdown').value;
    var quantity = getElement('quantityInput').value;

    // Add ingredient to the JavaScript array
    var ingredients = {
        ingredientID: ingredientID,
        quantity: quantity,
        unit: unitID
    };
    ingredientsArray.push(ingredients);
    displayIngredient(ingredients);

//====================================================================================================
    function displayIngredient(ingredients) {
        var ingredientListDiv = getElement('ingredientList');

        var ingredientItem = document.createElement('div');
        ingredientItem.innerHTML = ingredient + ' (' + quantity + ' ' + unit + ')';

        var removeButton = document.createElement('span');
        removeButton.innerHTML = 'X';
        removeButton.classList.add('remove-button');
        removeButton.addEventListener('click', function () {
            // Remove the ingredient from the array
            var index = ingredientsArray.indexOf(ingredients);
            console.log(index + "this is index");
            if (index > -1) {
                ingredientsArray.splice(index, 1);
            }

            // Remove the ingredient from the ingredientList div
            ingredientItem.remove();
        });

        ingredientItem.appendChild(removeButton);
        ingredientListDiv.appendChild(ingredientItem);
    }
//====================================================================================================
    // Clear the form inputs

    getElement('quantityInput').value = '';

});

// Submit event listener for the main form
getElement('myFormAdd').addEventListener('submit', function (event) {

    var hiddenInput = document.createElement('input');
    hiddenInput.type = 'hidden';
    hiddenInput.name = 'ingredientsArray';
    hiddenInput.value = JSON.stringify(ingredientsArray);
    getElement('myFormAdd').appendChild(hiddenInput);

});
function closeForm() {
    var popup = document.getElementById("popup");
    popup.style.display = "none";
}
var message = '<%=request.getAttribute("message")%>';
console.log(message);
if (message !== 'null' && message !== '') {
    var messageContainer = document.getElementById("messageContainer");
    messageContainer.innerHTML = message;
    var popup = document.getElementById("popup");
    popup.style.display = "block";
}