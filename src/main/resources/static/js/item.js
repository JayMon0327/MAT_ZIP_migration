$(document).ready(function() {
    let itemIndex = 1;

    $("#add-item").on("click", function() {
        let itemHtml = `
            <div class="form-group">
                <label for="items[${itemIndex}].name">아이템 ${itemIndex + 1} 이름</label>
                <input type="text" class="form-control" id="items[${itemIndex}].name" name="items[${itemIndex}].name" required>
                <label for="items[${itemIndex}].price">아이템 ${itemIndex + 1} 가격</label>
                <input type="number" class="form-control" id="items[${itemIndex}].price" name="items[${itemIndex}].price" required>
                <label for="items[${itemIndex}].stock">아이템 ${itemIndex + 1} 재고 수량</label>
                <input type="number" class="form-control" id="items[${itemIndex}].stock" name="items[${itemIndex}].stock" required>
            </div>
        `;
        $("#item-group").append(itemHtml);
        itemIndex++;
    });
});
