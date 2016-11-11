package hr.bm.report;

import java.util.ArrayList;
import java.util.List;

public class ReportExample extends AbstractReport {

	public ReportExample(String p_template) {
		super(p_template);
		addReportLabels();
		addReportData();
	}

	@Override
	void addReportLabels() {
		m_reportData.put("title", "Report Tilte");
	}

	@Override
	void addReportData() {
		m_reportData.put("body", "Hello world!");

		List<ListMember> items = new ArrayList<ListMember>();
		items.add(new ListMember(1, "ptica"));
		items.add(new ListMember(3, "mačka"));
		items.add(new ListMember(1, "pas"));
		m_reportData.put("items", items);
	}

	public class ListMember {

		private int order;
		private String description;

		ListMember(int order, String description) {
			this.order = order;
			this.description = description;
		}

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}

}
