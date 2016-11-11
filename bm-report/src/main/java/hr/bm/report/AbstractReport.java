package hr.bm.report;


import java.util.HashMap;
import java.util.Map;


public abstract class AbstractReport {

  protected Map<String, Object> m_reportData = new HashMap<String, Object>();
  protected String m_template;

  public AbstractReport(final String p_template) {
    m_template = p_template;
  }

  abstract void addReportLabels();

  abstract void addReportData();

  public Map<String, Object> getReportData() {
    return m_reportData;
  }

  public String getTemplate() {
    return m_template;
  }

}
