
		var override = setInterval(function () {
			if (zAu && zAu.cmd0) {
				var oldShowBusy = zAu.cmd0.showBusy;
				var oldClearBusy = zAu.cmd0.clearBusy;
				zAu.cmd0.showBusy = function (uuid, msg) {
					var cls, // class of original busy box
						custom, // the custom busy box
						$body = jq(document.body),
						bwid = $body.width()-5, // body width (slightly smaller)
						bhgh = $body.height()-5, // body height (slightly smaller)
						innerWid = 200, // width of inner content of custom busy box
						innerHgh = 30, // height of inner content of custom busy box
						wid, hgh, // width and height of busy box
						mt, ml, key; // margin-top, margin-left and mapping key
					// get the class of original busy box and mapping key
					if (arguments.length == 1 || !uuid) {
						cls = 'z-modal-mask';
						key = 'null'
					} else {
						cls = 'z-apply-mask';
						key = zk.Widget.$(uuid).uuid;
					}
					// do the original showBusy
					oldShowBusy.apply(this, arguments);
					// get the original busy box
					var $dom = jq('.'+cls);

					if ((custom = jq('$customBusy'))) { // custom busybox exists
						var outer = jq(custom.find('div').clone()[0]), // clone the content of custom busy box
							wid = $dom.width(), // get original width
							hgh = $dom.height(), // get original height
							inner; // the inner content

						// shrink the size if too large
						if (wid > bwid) wid = bwid;
						if (hgh > bhgh) hgh = bhgh;

						// put content into body
						document.body.appendChild(outer[0]);
						// get inner content
						inner = jq(outer.find('div')[0]);

						// apply style to outer content
						outer.css({
							left: $dom.offset().left + 'px',
							top: $dom.offset().top + 'px',
							width: wid + 'px',
							height: hgh + 'px'
						});
						outer[0].style.zIndex = $dom[0].style.zIndex;

						// calculate the appropriate margin
						ml = wid > innerWid? ($dom.outerWidth(true) - innerWid)/2 : 0;
						mt = hgh > innerHgh? ($dom.outerHeight(true) - innerHgh)/2 : 0;
						// apply the style to inner content
						inner.css({
							marginTop: mt + 'px',
							marginBottom: mt + 'px',
							marginLeft: ml + 'px',
							marginRight: ml + 'px'
						})
						
						$dom.parent()[0].style.display = 'none';
						// store the outer node with mapping key
						zAu.cmd0[key] = outer[0];
					}
				};
				zAu.cmd0.clearBusy = function (uuid) {
					var key,
						node;
					// get the mapping key
					if (!uuid)
						key = 'null'
					else
						key = zk.Widget.$(uuid).uuid;

					// get node and remove it by mapping key
					if ((node = zAu.cmd0[key])
						&& node.parentNode)
						node.parentNode.removeChild(node);
					
					delete node;
					oldClearBusy.apply(this, arguments);
				}
				clearInterval(override);
			}
		});
