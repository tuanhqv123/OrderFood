// Wait for DOM to be fully loaded
document.addEventListener("DOMContentLoaded", function () {
  console.log("DOM loaded - initializing components");

  // Initialize Bootstrap components
  initBootstrapComponents();

  // Initialize skeleton screens
  initSkeletonScreens();

  // Initialize quantity controls
  initQuantityControls();

  // Initialize menu edit modal
  initMenuEditModal();

  // Initialize cart functionality
  initCartFunctionality();
});

/**
 * Initialize Bootstrap components
 */
function initBootstrapComponents() {
  // Initialize tooltips
  var tooltipTriggerList = [].slice.call(
    document.querySelectorAll('[data-bs-toggle="tooltip"]')
  );
  tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
  });

  // Initialize popovers
  var popoverTriggerList = [].slice.call(
    document.querySelectorAll('[data-bs-toggle="popover"]')
  );
  popoverTriggerList.map(function (popoverTriggerEl) {
    return new bootstrap.Popover(popoverTriggerEl);
  });

  // Auto-dismiss alerts
  var alertList = [].slice.call(document.querySelectorAll(".alert"));
  alertList.map(function (alertEl) {
    setTimeout(function () {
      var alert = new bootstrap.Alert(alertEl);
      alert.close();
    }, 5000);
  });
}

/**
 * Initialize skeleton screens
 */
function initSkeletonScreens() {
  console.log("Initializing skeleton screens");

  // Show skeleton loaders
  document.querySelectorAll(".skeleton-loader").forEach((loader) => {
    const contentId = loader.getAttribute("data-content-id");
    if (contentId) {
      const content = document.getElementById(contentId);
      if (content) {
        // Hide content initially
        content.classList.add("d-none");

        // Show content after a delay
        setTimeout(() => {
          loader.classList.add("d-none");
          content.classList.remove("d-none");
        }, 1000);
      }
    }
  });

  // Check for any hidden content that might have been missed
  setTimeout(() => {
    const hiddenContents = document.querySelectorAll(
      '.d-none[id="menuContent"]'
    );
    hiddenContents.forEach((content) => {
      console.log("Found hidden content:", content.id);
      setTimeout(() => {
        content.classList.remove("d-none");
        console.log("Hidden content shown:", content.id);
      }, 500);
    });
  }, 1500);
}

/**
 * Initialize quantity controls
 */
function initQuantityControls() {
  // Quantity decrease buttons
  document.querySelectorAll(".decrease").forEach((button) => {
    button.addEventListener("click", function (e) {
      e.preventDefault();
      const quantityControl = this.closest(".quantity-control");
      const input = quantityControl.querySelector(".item-quantity");
      const currentValue = parseInt(input.value) || 1;
      if (currentValue > 1) {
        input.value = currentValue - 1;

        // If in cart, update cart quantity
        const cartItem = this.closest(".cart-item");
        if (cartItem) {
          const menuItemId = cartItem.getAttribute("data-item-id");
          const tableId = document
            .querySelector(".cart-sidebar")
            .getAttribute("data-table-id");
          if (menuItemId && tableId) {
            updateCartItemQuantity(tableId, menuItemId, currentValue - 1);
          }
        }
      }
    });
  });

  // Quantity increase buttons
  document.querySelectorAll(".increase").forEach((button) => {
    button.addEventListener("click", function (e) {
      e.preventDefault();
      const quantityControl = this.closest(".quantity-control");
      const input = quantityControl.querySelector(".item-quantity");
      const currentValue = parseInt(input.value) || 1;
      const max = parseInt(input.getAttribute("max")) || 10;
      if (currentValue < max) {
        input.value = currentValue + 1;

        // If in cart, update cart quantity
        const cartItem = this.closest(".cart-item");
        if (cartItem) {
          const menuItemId = cartItem.getAttribute("data-item-id");
          const tableId = document
            .querySelector(".cart-sidebar")
            .getAttribute("data-table-id");
          if (menuItemId && tableId) {
            updateCartItemQuantity(tableId, menuItemId, currentValue + 1);
          }
        }
      }
    });
  });

  // Manual input handling
  document.querySelectorAll(".item-quantity").forEach((input) => {
    input.addEventListener("change", function () {
      let value = parseInt(this.value) || 1;
      const max = parseInt(this.getAttribute("max")) || 10;

      // Ensure value is between 1 and max
      value = Math.max(1, Math.min(value, max));
      this.value = value;

      // If in cart, update cart quantity
      const cartItem = this.closest(".cart-item");
      if (cartItem) {
        const menuItemId = cartItem.getAttribute("data-item-id");
        const tableId = document
          .querySelector(".cart-sidebar")
          .getAttribute("data-table-id");
        if (menuItemId && tableId) {
          updateCartItemQuantity(tableId, menuItemId, value);
        }
      }
    });
  });
}

function updateCartItemQuantity(tableId, menuItemId, quantity) {
  const formData = new FormData();
  formData.append("tableId", tableId);
  formData.append("menuItemId", menuItemId);
  formData.append("quantity", quantity);

  fetch("/api/cart/update-quantity", {
    method: "POST",
    body: formData,
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success && data.cart) {
        updateCartSidebar(data.cart);
      } else {
        console.error(
          "Error updating cart quantity:",
          data.message || "Unknown error"
        );
      }
    })
    .catch((error) => {
      console.error("Error updating cart quantity:", error);
    });
}

/**
 * Initialize cart functionality
 */
function initCartFunctionality() {
  console.log("Initializing cart functionality");

  // Add to cart buttons
  document.querySelectorAll(".btn-add-to-cart").forEach((button) => {
    button.addEventListener("click", function () {
      const menuItemId = this.getAttribute("data-item-id");
      const tableId = this.getAttribute("data-table-id");

      if (!menuItemId || !tableId) {
        console.error("Missing data attributes for add-to-cart button:", this);
        return;
      }

      // Find quantity input
      let quantity = 1;
      try {
        const quantityInput =
          this.closest(".product-actions").querySelector(".item-quantity");
        if (quantityInput) {
          quantity = parseInt(quantityInput.value) || 1;
        }
      } catch (error) {
        console.error("Error getting quantity:", error);
      }

      console.log("Add to cart clicked:", { menuItemId, tableId, quantity });
      addToCart(tableId, menuItemId, quantity);
    });
  });

  // Remove from cart buttons
  document.querySelectorAll(".remove-item-btn").forEach((button) => {
    button.addEventListener("click", function () {
      const menuItemId = this.getAttribute("data-item-id");
      const tableId = this.getAttribute("data-table-id");

      if (!menuItemId || !tableId) {
        console.error("Missing data attributes for remove button:", this);
        return;
      }

      console.log("Remove from cart clicked:", { menuItemId, tableId });
      removeFromCart(tableId, menuItemId);
    });
  });

  // Place order button
  const placeOrderBtn = document.querySelector(".place-order-btn");
  if (placeOrderBtn) {
    placeOrderBtn.addEventListener("click", function () {
      const tableId = this.getAttribute("data-table-id");
      if (!tableId) {
        console.error("Missing table ID for place order button");
        return;
      }

      showOrderConfirmation(tableId);
    });
  }

  // Payment button
  const paymentBtn = document.querySelector(".payment-btn");
  if (paymentBtn) {
    paymentBtn.addEventListener("click", function () {
      const tableId = this.getAttribute("data-table-id");
      if (!tableId) {
        console.error("Missing table ID for payment button");
        return;
      }

      showPaymentModal(tableId);
    });
  }

  // Confirm order button
  const confirmOrderBtn = document.querySelector(".confirm-order-btn");
  if (confirmOrderBtn) {
    confirmOrderBtn.addEventListener("click", function () {
      placeOrder();
    });
  }

  // Confirm payment button
  const confirmPaymentBtn = document.querySelector(".confirm-payment-btn");
  if (confirmPaymentBtn) {
    confirmPaymentBtn.addEventListener("click", function () {
      processPayment();
    });
  }
}

/**
 * Add item to cart via AJAX
 */
function addToCart(tableId, menuItemId, quantity) {
  if (!tableId || !menuItemId) {
    console.error("Missing required parameters for addToCart:", {
      tableId,
      menuItemId,
    });
    return;
  }

  // Get quantity from input
  const quantityInput = document.querySelector(
    `.item-quantity[data-item-id="${menuItemId}"]`
  );

  if (quantityInput) {
    quantity = parseInt(quantityInput.value) || 1;
    quantity = Math.max(1, Math.min(quantity, 10));
  } else {
    quantity = 1;
  }

  console.log("Adding to cart:", { tableId, menuItemId, quantity });

  const formData = new FormData();
  formData.append("tableId", tableId);
  formData.append("menuItemId", menuItemId);
  formData.append("quantity", quantity);

  fetch("/api/cart/add", {
    method: "POST",
    body: formData,
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    })
    .then((data) => {
      console.log("Cart response:", data);
      if (data.success) {
        // Reset quantity input to 1 after successful add
        if (quantityInput) {
          quantityInput.value = 1;
        }

        // Update cart sidebar
        if (data.cart) {
          updateCartSidebar(data.cart);
        } else {
          console.error("Cart data missing in response:", data);
        }
      } else {
        console.error("Error adding to cart:", data.message || "Unknown error");
      }
    })
    .catch((error) => {
      console.error("Error adding to cart:", error);
    });
}

/**
 * Remove item from cart via AJAX
 */
function removeFromCart(tableId, menuItemId) {
  const formData = new FormData();
  formData.append("tableId", tableId);
  formData.append("menuItemId", menuItemId);

  fetch("/api/cart/remove", {
    method: "POST",
    body: formData,
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        // Reset quantity input to 1
        const quantityInput = document.querySelector(
          `.item-quantity[data-item-id="${menuItemId}"]`
        );
        if (quantityInput) {
          quantityInput.value = 1;
        }

        // Update cart sidebar
        updateCartSidebar(data.cart);
      } else {
        console.error(
          "Error removing from cart:",
          data.message || "Unknown error"
        );
      }
    })
    .catch((error) => {
      console.error("Error removing from cart:", error);
    });
}

/**
 * Update cart sidebar with new cart data
 */
function updateCartSidebar(cart) {
  console.log("Updating cart sidebar with data:", cart);

  // Validate cart object
  if (!cart) {
    console.error("Invalid cart object:", cart);
    return;
  }

  const cartSidebar = document.getElementById("cartSidebar");
  if (!cartSidebar) {
    console.error("Cart sidebar element not found");
    return;
  }

  // Ensure cart items container exists
  let cartItemsContainer = cartSidebar.querySelector(".cart-items");
  if (!cartItemsContainer) {
    cartItemsContainer = document.createElement("div");
    cartItemsContainer.className = "cart-items";
    cartSidebar.appendChild(cartItemsContainer);
  }

  const emptyCartMessage = document.getElementById("emptyCartMessage");
  const cartItemsList = document.getElementById("cartItemsList");

  // Update cart items
  if (!cart.items || cart.items.length === 0) {
    // Show empty cart message
    if (emptyCartMessage) {
      emptyCartMessage.classList.remove("d-none");
    }
    if (cartItemsList) {
      cartItemsList.classList.add("d-none");
    }
    const cartSummary = cartSidebar.querySelector(".cart-summary");
    if (cartSummary) cartSummary.classList.add("d-none");
    const hr = cartSidebar.querySelector("hr");
    if (hr) hr.classList.add("d-none");
  } else {
    // Hide empty cart message
    if (emptyCartMessage) emptyCartMessage.classList.add("d-none");

    // Create or update cart items list
    let itemsList = cartItemsList;
    if (!itemsList) {
      itemsList = document.createElement("div");
      itemsList.id = "cartItemsList";
      cartItemsContainer.appendChild(itemsList);
    }
    itemsList.innerHTML = ""; // Clear existing items

    // Add each item to the cart
    cart.items.forEach((item) => {
      if (!item || !item.menuItem) {
        console.error("Invalid cart item:", item);
        return;
      }

      const cartItem = document.createElement("div");
      cartItem.className = "cart-item mb-3";
      cartItem.setAttribute("data-item-id", item.menuItem.id);

      cartItem.innerHTML = `
        <img src="${item.menuItem.imageUrl}" class="cart-item-image" alt="${
        item.menuItem.name
      }" />
        <div class="cart-item-details">
          <h6 class="cart-item-name">${item.menuItem.name}</h6>
          <div class="cart-item-price">${formatPrice(
            item.menuItem.price
          )} VND</div>
        </div>
        <div class="cart-item-actions">
          <div class="quantity-control">
            <div class="btn-decrease">
              <button type="button" class="btn decrease">
                <i class="fas fa-minus"></i>
              </button>
            </div>
            <div class="input-wrapper">
              <input type="number" class="form-control text-center item-quantity" 
                     value="${item.quantity}" min="1" max="10" 
                     data-item-id="${item.menuItem.id}" />
            </div>
            <div class="btn-increase">
              <button type="button" class="btn increase">
                <i class="fas fa-plus"></i>
              </button>
            </div>
          </div>
          <button class="btn btn-sm remove-item-btn"
                  data-item-id="${item.menuItem.id}" 
                  data-table-id="${cart.tableId}">
            <i class="fas fa-times"></i>
          </button>
        </div>
      `;

      itemsList.appendChild(cartItem);
    });

    itemsList.classList.remove("d-none");

    // Update cart summary
    let cartSummary = cartSidebar.querySelector(".cart-summary");
    if (!cartSummary) {
      cartSummary = document.createElement("div");
      cartSummary.className = "cart-summary";
      cartSidebar.appendChild(cartSummary);
    }

    cartSummary.innerHTML = `
      <div class="d-flex justify-content-between mb-2">
        <span class="total-label">Tổng cộng:</span>
        <span class="total-amount" id="cartTotal">${formatPrice(
          cart.total
        )} VND</span>
      </div>
      <div class="d-grid gap-2">
        <button type="button" class="btn place-order-btn" data-table-id="${
          cart.tableId
        }">
          <i class="fas fa-utensils"></i> Đặt món
        </button>
        <button type="button" class="btn btn-payment payment-btn" data-table-id="${
          cart.tableId
        }">
          <i class="fas fa-credit-card"></i> Thanh toán
        </button>
      </div>
    `;

    cartSummary.classList.remove("d-none");

    // Show separator
    const hr = cartSidebar.querySelector("hr");
    if (hr) {
      hr.classList.remove("d-none");
    } else {
      const newHr = document.createElement("hr");
      cartSidebar.insertBefore(newHr, cartSummary);
    }

    // Add event listeners
    initQuantityControls();
    initCartButtons();
  }
}

/**
 * Show order confirmation modal
 */
function showOrderConfirmation(tableId) {
  // Get cart items
  fetch(`/api/cart/${tableId}`)
    .then((response) => response.json())
    .then((cart) => {
      if (!cart || !cart.items || cart.items.length === 0) {
        console.error("Cart is empty");
        return;
      }

      // Populate order confirmation modal
      const orderConfirmItems = document.getElementById("orderConfirmItems");
      if (orderConfirmItems) {
        orderConfirmItems.innerHTML = "";

        // Add header
        const header = document.createElement("div");
        header.className = "order-confirm-header";
        header.innerHTML = `
          <div class="item-name">Tên món</div>
          <div class="item-quantity">Số lượng</div>
          <div class="item-price">Giá</div>
        `;
        orderConfirmItems.appendChild(header);

        // Create items list
        const itemsList = document.createElement("div");
        itemsList.className = "list-group";

        cart.items.forEach((item) => {
          if (!item || !item.menuItem) return;

          const listItem = document.createElement("div");
          listItem.className = "list-group-item";
          listItem.innerHTML = `
            <div class="item-name">${item.menuItem.name}</div>
            <div class="item-quantity">${item.quantity}</div>
            <div class="item-price">${formatPrice(
              item.menuItem.price * item.quantity
            )} VND</div>
          `;

          itemsList.appendChild(listItem);
        });

        // Add total
        const totalItem = document.createElement("div");
        totalItem.className = "list-group-item total";
        totalItem.innerHTML = `
          <div class="item-name">Tổng cộng</div>
          <div class="item-quantity"></div>
          <div class="item-price">${formatPrice(cart.total)} VND</div>
        `;

        itemsList.appendChild(totalItem);
        orderConfirmItems.appendChild(itemsList);
      }

      // Show modal
      const orderConfirmModal = new bootstrap.Modal(
        document.getElementById("orderConfirmModal")
      );
      orderConfirmModal.show();
    })
    .catch((error) => {
      console.error("Error fetching cart for order confirmation:", error);
    });
}

/**
 * Place order
 */
function placeOrder() {
  // Get table ID from the button
  const tableId = document
    .querySelector(".place-order-btn")
    .getAttribute("data-table-id");
  if (!tableId) {
    console.error("Table ID not found");
    return;
  }

  // Create order via API
  fetch("/api/orders/create", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ tableId: tableId }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        // Hide order confirmation modal
        const orderConfirmModal = bootstrap.Modal.getInstance(
          document.getElementById("orderConfirmModal")
        );
        if (orderConfirmModal) {
          orderConfirmModal.hide();
        }

        // Show order success modal
        const orderNumber = document.getElementById("orderNumber");
        if (orderNumber) {
          orderNumber.textContent = data.orderId || "Unknown";
        }

        const orderSuccessModal = new bootstrap.Modal(
          document.getElementById("orderSuccessModal")
        );
        orderSuccessModal.show();

        // Clear cart
        updateCartSidebar({ items: [], total: 0, tableId: tableId });
      } else {
        console.error("Error placing order:", data.message || "Unknown error");
      }
    })
    .catch((error) => {
      console.error("Error placing order:", error);
    });
}

/**
 * Show payment modal
 */
function showPaymentModal(tableId) {
  // Get cart items
  fetch(`/api/cart/${tableId}`)
    .then((response) => response.json())
    .then((cart) => {
      if (!cart || !cart.items || cart.items.length === 0) {
        console.error("Cart is empty");
        return;
      }

      // Populate payment modal
      const billItems = document.getElementById("billItems");
      const billTotal = document.getElementById("billTotal");

      if (billItems) {
        billItems.innerHTML = "";

        cart.items.forEach((item) => {
          if (!item || !item.menuItem) return;

          const itemRow = document.createElement("div");
          itemRow.className = "item-row";
          itemRow.innerHTML = `
            <span>${item.menuItem.name} x ${item.quantity}</span>
            <span>${formatPrice(item.menuItem.price * item.quantity)} VND</span>
          `;

          billItems.appendChild(itemRow);
        });
      }

      if (billTotal) {
        billTotal.textContent = `${formatPrice(cart.total)} VND`;
      }

      // Show modal
      const paymentModal = new bootstrap.Modal(
        document.getElementById("paymentModal")
      );
      paymentModal.show();
    })
    .catch((error) => {
      console.error("Error fetching cart for payment:", error);
    });
}

/**
 * Process payment
 */
function processPayment() {
  // Get table ID from the button
  const tableId = document
    .querySelector(".payment-btn")
    .getAttribute("data-table-id");
  if (!tableId) {
    console.error("Table ID not found");
    return;
  }

  // Get phone number if provided
  const phoneNumber = document.getElementById("phoneNumber").value;

  // Process payment via API
  fetch("/api/payments/process", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      tableId: tableId,
      phoneNumber: phoneNumber || null,
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        // Hide payment modal
        const paymentModal = bootstrap.Modal.getInstance(
          document.getElementById("paymentModal")
        );
        if (paymentModal) {
          paymentModal.hide();
        }

        // Show payment success modal
        const invoiceNumber = document.getElementById("invoiceNumber");
        if (invoiceNumber) {
          invoiceNumber.textContent =
            data.invoiceId || "INV-" + Math.floor(Math.random() * 100000);
        }

        const paymentSuccessModal = new bootstrap.Modal(
          document.getElementById("paymentSuccessModal")
        );
        paymentSuccessModal.show();

        // Clear cart
        updateCartSidebar({ items: [], total: 0, tableId: tableId });
      } else {
        console.error(
          "Error processing payment:",
          data.message || "Unknown error"
        );
      }
    })
    .catch((error) => {
      console.error("Error processing payment:", error);
    });
}

/**
 * Format price with thousands separator
 */
function formatPrice(price) {
  return new Intl.NumberFormat("vi-VN").format(price);
}

/**
 * Initialize menu edit modal
 */
function initMenuEditModal() {
  // Edit menu item buttons in admin view
  document.querySelectorAll(".edit-menu-item-btn").forEach((button) => {
    button.addEventListener("click", function () {
      const menuItemId = this.getAttribute("data-item-id");
      const menuEditModal = document.getElementById("menuEditModal");

      if (menuItemId && menuEditModal) {
        // Set the menu item ID in the form
        const menuItemIdInput = menuEditModal.querySelector(
          'input[name="menuItemId"]'
        );
        if (menuItemIdInput) {
          menuItemIdInput.value = menuItemId;
        }

        // Show the modal
        const modal = new bootstrap.Modal(menuEditModal);
        modal.show();
      }
    });
  });

  // Delete menu item buttons in admin view
  document.querySelectorAll(".delete-menu-item-btn").forEach((button) => {
    button.addEventListener("click", function () {
      const menuItemId = this.getAttribute("data-item-id");
      const menuItemName = this.getAttribute("data-item-name");
      const deleteConfirmModal = document.getElementById("deleteConfirmModal");

      if (menuItemId && deleteConfirmModal) {
        // Set the menu item ID in the form
        const menuItemIdInput = deleteConfirmModal.querySelector(
          'input[name="menuItemId"]'
        );
        if (menuItemIdInput) {
          menuItemIdInput.value = menuItemId;
        }

        // Set the menu item name in the confirmation message
        const confirmMessage =
          deleteConfirmModal.querySelector(".confirm-message");
        if (confirmMessage && menuItemName) {
          confirmMessage.textContent = `Are you sure you want to delete "${menuItemName}"?`;
        }

        // Show the modal
        const modal = new bootstrap.Modal(deleteConfirmModal);
        modal.show();
      }
    });
  });
}

/**
 * Select all checkboxes
 */
function toggleSelectAll(source) {
  const checkboxes = document.querySelectorAll(".item-checkbox");
  checkboxes.forEach((checkbox) => {
    checkbox.checked = source.checked;
  });
}

// Add this new function to initialize cart buttons
function initCartButtons() {
  // Place order button
  document.querySelectorAll(".place-order-btn").forEach((btn) => {
    btn.addEventListener("click", function () {
      const tableId = this.getAttribute("data-table-id");
      if (tableId) showOrderConfirmation(tableId);
    });
  });

  // Payment button
  document.querySelectorAll(".payment-btn").forEach((btn) => {
    btn.addEventListener("click", function () {
      const tableId = this.getAttribute("data-table-id");
      if (tableId) showPaymentModal(tableId);
    });
  });

  // Remove item buttons
  document.querySelectorAll(".remove-item-btn").forEach((btn) => {
    btn.addEventListener("click", function () {
      const menuItemId = this.getAttribute("data-item-id");
      const tableId = this.getAttribute("data-table-id");
      if (menuItemId && tableId) removeFromCart(tableId, menuItemId);
    });
  });
}
