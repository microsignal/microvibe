
* 全文索引
  * <a class="demo-search-clear">清理</a>
* 主题切换
  * <a class="demo-theme-preview" data-theme="vue">vue</a>
  * <a class="demo-theme-preview" data-theme="buble">buble</a>
  * <a class="demo-theme-preview" data-theme="dark">dark</a>
  * <a class="demo-theme-preview" data-theme="pure">pure</a>

<script>
		(function(){
			bindThemeChangeEvent();
			bindSearchClearEvent();
		})();
		function bindSearchClearEvent(){
			if(typeof(Docsify) == 'undefined' || Docsify.dom.find('.demo-search-clear') == null){
				setTimeout(function(){
					bindSearchClearEvent();
				},1000);
				return false;
			}
			var searchClear = Docsify.dom.find('.demo-search-clear');
			searchClear.onclick = function(e){
				localStorage.removeItem('docsify.search.expires');
				localStorage.removeItem('docsify.search.index');
			}
		}
		function bindThemeChangeEvent(){
			if(typeof(Docsify) == 'undefined' || Docsify.dom.findAll('.demo-theme-preview').length == 0){
				setTimeout(function(){
					bindThemeChangeEvent();
				},1000);
				return false;
			}
			var previews = Docsify.dom.findAll('.demo-theme-preview');
			var themes = Docsify.dom.findAll('[rel="stylesheet"]');
			previews.forEach(function(preview) {
				preview.onclick = function(e) {
					var title = e.target.getAttribute('data-theme')
					themes.forEach(function(theme) {
						theme.disabled = theme.title !== title
					});
				};
			});
</script>