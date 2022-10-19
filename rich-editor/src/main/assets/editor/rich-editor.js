/**
 * 富文本编辑器的js
 * 主要用于交互的
 */
const RE = {};

RE.currentSelection = {
    "startContainer": 0,
    "startOffset": 0,
    "endContainer": 0,
    "endOffset": 0
};

RE.editor = document.getElementById('editor');
RE.placeholder = document.getElementById('placeholder');

document.addEventListener("selectionchange", function() { RE.backuprange(); });

// Initializations
RE.callback = function() {
    window.location.href = "re-callback://"+RE.getHtml();
    RE.enabledEditingItems();
}
// Initializations
RE.focusCallback = function() {
    window.location.href = "re-focus-callback://";
}

RE.setHtml = function(contents) {
    RE.editor.innerHTML = decodeURIComponent(contents.replace(/\+/g, '%20'));
}

RE.getHtml = function() {
    let html = RE.editor.innerHTML
    html = encodeURIComponent(html)
    return html;
}

RE.getText = function() {
    return RE.editor.innerText;
}

RE.setBaseTextColor = function(color) {
    RE.editor.style.color  = color;
}

RE.setBaseFontSize = function(size) {
    RE.editor.style.fontSize = size;
}

RE.setPadding = function(left, top, right, bottom) {
  RE.setElementPadding(RE.editor, left, top, right, bottom)
  RE.setElementPadding(RE.placeholder, left, top, right, bottom)
}

RE.setElementPadding = function(element, left, top, right, bottom) {
  element.style.paddingLeft = left;
  element.style.paddingTop = top;
  element.style.paddingRight = right;
  element.style.paddingBottom = bottom;
}


RE.setBackgroundColor = function(color) {
    document.body.style.backgroundColor = color;
}

RE.setBackgroundImage = function(image) {
    RE.editor.style.backgroundImage = image;
}

RE.setWidth = function(size) {
    RE.editor.style.minWidth = size;
}

RE.setHeight = function(size) {
    RE.editor.style.height = size;
}

RE.setTextAlign = function(align) {
    RE.editor.style.textAlign = align;
}

RE.setVerticalAlign = function(align) {
    RE.editor.style.verticalAlign = align;
}

RE.setPlaceholder = function(placeholder) {
    RE.placeholder.innerHTML = placeholder
}

RE.setInputEnabled = function(inputEnabled) {
    RE.editor.contentEditable = String(inputEnabled);
}

RE.undo = function() {
    document.execCommand('undo', false, null);
}

RE.redo = function() {
    document.execCommand('redo', false, null);
}

RE.setBold = function() {
    document.execCommand('bold', false, null);
}

RE.setItalic = function() {
    document.execCommand('italic', false, null);
}

RE.setSubscript = function() {
    document.execCommand('subscript', false, null);
}

RE.setSuperscript = function() {
    document.execCommand('superscript', false, null);
}

RE.setStrikeThrough = function() {
    document.execCommand('strikeThrough', false, null);
}

RE.setUnderline = function() {
    document.execCommand('underline', false, null);
}

RE.setBullets = function() {
    document.execCommand('insertUnorderedList', false, null);
    RE.rangeToEnd();
}

RE.setNumbers = function() {
    document.execCommand('insertOrderedList', false, null);
    RE.rangeToEnd();
}

RE.setTextColor = function(color) {
    // RE.prepareInsert();
    // RE.restorerange();
    document.execCommand("styleWithCSS", null, true);
    document.execCommand('foreColor', false, color);
    document.execCommand("styleWithCSS", null, false);
}

RE.setTextBackgroundColor = function(color) {
    // RE.prepareInsert();
    // RE.restorerange();
    document.execCommand("styleWithCSS", null, true);
    document.execCommand('hiliteColor', false, color);
    document.execCommand("styleWithCSS", null, false);
}

RE.setFontSize = function(fontSize){
    document.execCommand("fontSize", false, fontSize);
}

RE.setHeading = function(heading) {
    document.execCommand('formatBlock', false, '<h'+heading+'>');
}

RE.setIndent = function() {
    document.execCommand('indent', false, null);
}

RE.setOutdent = function() {
    document.execCommand('outdent', false, null);
}

RE.setJustifyLeft = function() {
    document.execCommand('justifyLeft', false, null);
}

RE.setJustifyCenter = function() {
    document.execCommand('justifyCenter', false, null);
}

RE.setJustifyRight = function() {
    document.execCommand('justifyRight', false, null);
}

RE.setBlockquote = function() {
    document.execCommand('formatBlock', false, '<blockquote>');
    RE.rangeToEnd();
}

RE.insertImage = function(url, alt) {
    RE.prepareInsert();
    let html = '<img src="' + url + '" alt="' + alt + '"/>';
    RE.insertHTML(html);
}

/**插入一个空白字符，目的是诸如subscribe、superscript会有bug，无法取消，因此再后面插一个空白字符*/
RE.insertBlank = function() {
    // RE.prepareInsert();
    let html = '<div>&nbsp;</div>';
    RE.insertHTML(html);
}
/**插入一个空行，目的是诸如blockquote会有bug，无法取消*/
RE.insertBlankLine = function() {
    // RE.prepareInsert();
    let html = '<br/>';
    RE.insertHTML(html);
}

RE.insertHTML = function(html) {
    // RE.restorerange();
    document.execCommand('insertHTML', false, html);
    // RE.editor.innerHTML = RE.editor.innerHTML + html
}

RE.insertLink = function(url, title) {
    // RE.prepareInsert();
    // RE.restorerange();
    const sel = document.getSelection();
    if (sel.toString().length === 0) {
        document.execCommand("insertHTML",false,"<a href='"+url+"'>"+title+"</a>");
    } else if (sel.rangeCount) {
       var el = document.createElement("a");
       el.setAttribute("href", url);
       el.setAttribute("title", title);

        const range = sel.getRangeAt(0).cloneRange();
        range.surroundContents(el);
       sel.removeAllRanges();
       sel.addRange(range);
   }
    RE.callback();
}

RE.setTodo = function(text) {
    let html = '<input type="checkbox" name="'+ text +'" value="'+ text +'"/> &nbsp;';
    RE.insertHTML(html)
    document.execCommand('insertHTML', false, html);
}

RE.prepareInsert = function() {
    RE.backuprange();
}


/**移动光标到当前行的末尾*/
RE.rangeToEnd = function(){
    let selection = window.getSelection()
    //不存在光标，直接return
    if(selection.rangeCount <= 0){
        return
    }
    //获取第0个光标所在位置
    let currentRange = selection.getRangeAt(0)
    let container = currentRange.commonAncestorContainer
    if(!container){
        console.error("获取不到光标所在的元素：", currentRange)
        return
    }
    let range = document.createRange()
    range.setStart(container, container.length);
    range.collapse(true);
    selection.removeAllRanges()
    selection.addRange(range);
}

RE.backuprange = function(){
    const selection = window.getSelection();
    if (selection.rangeCount > 0) {
      const range = selection.getRangeAt(0);
      RE.currentSelection = {
          "startContainer": range.startContainer,
          "startOffset": range.startOffset,
          "endContainer": range.endContainer,
          "endOffset": range.endOffset};
    }
}

RE.restorerange = function(){
    if(RE.currentSelection.startContainer === 0){
        return
    }
    const selection = window.getSelection();
    selection.removeAllRanges();
    const range = document.createRange();
    range.setStart(RE.currentSelection.startContainer, RE.currentSelection.startOffset);
    range.setEnd(RE.currentSelection.endContainer, RE.currentSelection.endOffset);
    selection.addRange(range);
}

/**提示页面切换了当前的状态*/
RE.enabledEditingItems = function() {
    const items = [];
    if (document.queryCommandState('bold')) {
        items.push('bold');
    }
    if (document.queryCommandState('italic')) {
        items.push('italic');
    }
    if (document.queryCommandState('subscript')) {
        items.push('subscript');
    }
    if (document.queryCommandState('superscript')) {
        items.push('superscript');
    }
    if (document.queryCommandState('strikeThrough')) {
        items.push('strikeThrough');
    }
    if (document.queryCommandState('underline')) {
        items.push('underline');
    }
    if (document.queryCommandState('insertOrderedList')) {
        items.push('orderedList');
    }
    if (document.queryCommandState('insertUnorderedList')) {
        items.push('unorderedList');
    }
    if (document.queryCommandState('justifyCenter')) {
        items.push('justifyCenter');
    }
    if (document.queryCommandState('justifyLeft')) {
        items.push('justifyLeft');
    }
    if (document.queryCommandState('justifyRight')) {
        items.push('justifyRight');
    }
    if (document.queryCommandState('insertHorizontalRule')) {
        items.push('horizontalRule');
    }
    if (document.queryCommandState('fontSize')) {
        items.push('fontSize');
    }
    const formatBlock = document.queryCommandValue('formatBlock');
    if (formatBlock.length > 0) {
        items.push(formatBlock);
    }

    window.location.href = "re-state://" + encodeURI(items.join(','));
}


RE.focus = function() {
    const range = document.createRange();
    range.selectNodeContents(RE.editor);
    range.collapse(false);
    const selection = window.getSelection();
    selection.removeAllRanges();
    selection.addRange(range);
    RE.editor.focus();
}

RE.blurFocus = function() {
    RE.editor.blur();
}

RE.removeFormat = function() {
    document.execCommand('removeFormat', false, null);
}

// Event Listeners
RE.editor.addEventListener("input", RE.callback);
RE.editor.addEventListener("focus", RE.focusCallback);
RE.editor.addEventListener("keyup", function(e) {
    const KEY_LEFT = 37, KEY_RIGHT = 39;
    if (e.which === KEY_LEFT || e.which === KEY_RIGHT) {
        RE.enabledEditingItems(e);
    }
});
RE.editor.addEventListener("click", RE.enabledEditingItems);
