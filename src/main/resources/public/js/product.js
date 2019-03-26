/**
 * Provide the logics for the interaction with Product page.
 */

function ProductSearch() {
	var self = this;
	this.productSearchDataTable = $('#productSeach_DateTable').DataTable({
		"columnDefs" : [ {
			"name" : "price",
			"targets" : 5,
			"data" : "price",
			"render" : function(data, type, full, meta) {
				return self.formatNumberWithSign(data, "$");
				;
			}
		} ]
	});
}

ProductSearch.prototype = {
	formatNumber : function(data) {
		return data.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	},
	formatNumberWithSign : function(data, sign) {
		if ($.isNumeric(data) == false)
			return "";

		var fromattedNum = this.formatNumber(data);
		return fromattedNum = sign + fromattedNum;
	}
}