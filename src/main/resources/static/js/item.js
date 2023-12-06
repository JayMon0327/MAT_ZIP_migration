let itemIndex = 1; // 이미 두 개의 아이템이 있으므로, 인덱스는 2부터 시작합니다.

$("#add-item").on("click", function() {
    let itemHtml = `
        <div class="form-group">
            <label for="itemDtos[${itemIndex}].name">아이템 ${itemIndex + 1} 이름</label>
            <input type="text" class="form-control" id="itemDtos[${itemIndex}].name" name="itemDtos[${itemIndex}].name" required>
        </div>
      `;
    $(".item-group").append(itemHtml);
    itemIndex++;
});