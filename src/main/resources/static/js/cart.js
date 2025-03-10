// Cart Functions
function updateQuantity(button, change) {
  const tableId = button.dataset.tableId;
  const menuItemId = button.dataset.itemId;
  const quantityInput = button.parentElement.querySelector(".item-quantity");
  let newQuantity = parseInt(quantityInput.value) + change;

  // Ensure quantity is within valid range
  newQuantity = Math.max(1, Math.min(10, newQuantity));

  // Update cart on server
  fetch(
    `/cart/update?tableId=${tableId}&menuItemId=${menuItemId}&quantity=${newQuantity}`,
    {
      method: "POST",
    }
  )
    .then((response) => response.text())
    .then((html) => {
      document.querySelector(".cart-sidebar").innerHTML = html;
    })
    .catch((error) => console.error("Error:", error));
}

function handleQuantityChange(input) {
  const tableId = input.dataset.tableId;
  const menuItemId = input.dataset.itemId;
  let quantity = parseInt(input.value);

  // Ensure quantity is within valid range
  quantity = Math.max(1, Math.min(10, quantity));
  input.value = quantity;

  // Update cart on server
  fetch(
    `/cart/update?tableId=${tableId}&menuItemId=${menuItemId}&quantity=${quantity}`,
    {
      method: "POST",
    }
  )
    .then((response) => response.text())
    .then((html) => {
      document.querySelector(".cart-sidebar").innerHTML = html;
    })
    .catch((error) => console.error("Error:", error));
}

function removeFromCart(button) {
  const tableId = button.dataset.tableId;
  const menuItemId = button.dataset.itemId;

  fetch(`/cart/remove?tableId=${tableId}&menuItemId=${menuItemId}`, {
    method: "POST",
  })
    .then((response) => response.text())
    .then((html) => {
      document.querySelector(".cart-sidebar").innerHTML = html;

      // Reset quantity input in menu item card
      const menuItemQuantityInput = document.querySelector(
        `.item-quantity[data-item-id="${menuItemId}"]`
      );
      if (menuItemQuantityInput) {
        menuItemQuantityInput.value = 1;
      }
    })
    .catch((error) => console.error("Error:", error));
}

function addToCart(button) {
  const tableId = button.dataset.tableId;
  const menuItemId = button.dataset.itemId;
  const quantityInput = button
    .closest(".product-actions")
    .querySelector(".item-quantity");
  const quantity = parseInt(quantityInput.value);

  fetch(
    `/cart/add?tableId=${tableId}&menuItemId=${menuItemId}&quantity=${quantity}`,
    {
      method: "POST",
    }
  )
    .then((response) => response.text())
    .then((html) => {
      document.querySelector(".cart-sidebar").innerHTML = html;
      // Reset quantity to 1 after adding to cart
      quantityInput.value = 1;
    })
    .catch((error) => console.error("Error:", error));
}

function showOrderConfirmation(button) {
  const tableId = button.dataset.tableId;

  fetch(`/order/confirm?tableId=${tableId}`)
    .then((response) => response.text())
    .then((html) => {
      const modalContent = document.querySelector(
        "#orderConfirmationModal .modal-content"
      );
      modalContent.innerHTML = html;
      new bootstrap.Modal(
        document.getElementById("orderConfirmationModal")
      ).show();
    })
    .catch((error) => console.error("Error:", error));
}
