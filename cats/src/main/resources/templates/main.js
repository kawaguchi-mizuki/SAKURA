document.querySelector('.gacha-img').onclick = function() {
  //alert('ガチャクリック');
  var category = document.querySelector('.gacha-category').value;

  if(category == '') {
    alert('カテゴリーを選択してください');
  } else {
    //
    //document.querySelector('.gacha-message').classList.remove('passive');
    document.querySelector('.gacha-message').classList.remove('fadeout');
    document.querySelector('.gacha-message').classList.add('fadein');
    //document.querySelector('.gacha-img').classList.remove('active');
  }
}

document.querySelector('.img-heart').onclick = function() {
  document.querySelector('.gacha-message').classList.remove('fadein');
  document.querySelector('.gacha-message').classList.add('fadeout');
}
