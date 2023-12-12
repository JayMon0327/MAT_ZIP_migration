  // 구매 수량 +, - 증가 시키기
  document.getElementById('productSelect').addEventListener('change', function() {
    var selectedProductId = this.value;
    if (selectedProductId && !document.getElementById('quantity' + selectedProductId)) {
      var selectedProductInfo = document.getElementById('selectedProducts');
      var selectedProductElement = document.createElement('div');
      selectedProductElement.setAttribute('data-product-id', selectedProductId);

      selectedProductElement.innerHTML = this.options[this.selectedIndex].text;

      // 상품 이름만을 담는 span 요소 생성
      var productNameSpan = document.createElement('span');
      productNameSpan.innerText = this.options[this.selectedIndex].text;
      selectedProductElement.appendChild(productNameSpan);

      var quantitySpan = document.createElement('span');
      quantitySpan.innerText = "1";
      quantitySpan.id = "quantity" + selectedProductId;

      var minusButton = document.createElement('button');
      minusButton.innerText = "-";
      minusButton.onclick = function() { changeQuantity(selectedProductId, -1); };

      var plusButton = document.createElement('button');
      plusButton.innerText = "+";
      plusButton.onclick = function() { changeQuantity(selectedProductId, 1); };

      selectedProductElement.appendChild(minusButton);
      selectedProductElement.appendChild(quantitySpan);
      selectedProductElement.appendChild(plusButton);

      selectedProductInfo.appendChild(selectedProductElement);
    }
  });

  // 수량 선택은 1이상
  function changeQuantity(productId, amount) {
    var quantitySpan = document.getElementById('quantity' + productId);
    var quantity = parseInt(quantitySpan.innerText);
    quantity = Math.max(quantity + amount, 1); // 수량은 1 이상이어야 합니다.
    quantitySpan.innerText = quantity;
  }

    // <주문하기> 버튼 누르면 서버로 전송
    document.getElementById('orderButton').addEventListener('click', function() {
      var orderForm = document.getElementById('orderForm');
      var selectedProductInfo = document.getElementById('selectedProducts').children;

      for (var i = 0; i < selectedProductInfo.length; i++) {
        var productId = selectedProductInfo[i].getAttribute('data-product-id');
        var quantity = document.getElementById('quantity' + productId).innerText;

        // 상품 ID 추가
        var productIdInput = document.createElement('input');
        productIdInput.type = 'hidden';
        productIdInput.name = 'items[' + i + '].itemId';
        productIdInput.value = productId;
        orderForm.appendChild(productIdInput);

        // 상품 수량 추가
        var quantityInput = document.createElement('input');
        quantityInput.type = 'hidden';
        quantityInput.name = 'items[' + i + '].itemStock';
        quantityInput.value = quantity;
        orderForm.appendChild(quantityInput);

        // 상품 이름 추출 및 추가
        var productName = selectedProductInfo[i].querySelector('span').innerText;
        var productNameInput = document.createElement('input');
        productNameInput.type = 'hidden';
        productNameInput.name = 'items[' + i + '].itemName';
        productNameInput.value = productName;
        orderForm.appendChild(productNameInput);
      }

      // 판매자 ID 추가
      var sellerIdInput = document.createElement('input');
      sellerIdInput.type = 'hidden';
      sellerIdInput.name = 'sellerName';
      sellerIdInput.value = '${products.member.username}';
      orderForm.appendChild(sellerIdInput);

      orderForm.submit();
});