package com.ipaas.monitoringplstformsys.common.constant;

import java.util.HashSet;
import java.util.Set;

public interface DmlConstant {


    String DEFAULT_MYSQL_NAME = "MySQL_Database_Config";

    String DEFAULT_ORACLE_NAME = "Oracle_Database_Config";

    String DEFAULT_SQL_SERVER_NAME = "SQLServer_Database_Config";

    String INSERT_STR = "insert";

    String UPDATE_STR = "update";

    String DELETE_STR = "delete";

    String SELECT_STR = "select";

    String EMPTY_STR = " ";

    String WHERE_STR = "where";

    String COLUMN_STR = "column";

    String TABLE_STR = "table";

    String LEFT_BRACKETS = "(";

    String RIGHT_BRACKETS = ")";

    String COMMA = ",";

    String COLON = ":";

    String WELL = "#";

    String STOP_SIGN = "、";

    String LEFT_MID_BRACKET = "[";

    String RIGHT_MID_BRACKET = "]";

    String LEFT_BIG_BRACKET = "{";

    String RIGHT_BIG_BRACKET = "}";

    String LEFT_ANGLE_BRACKET = "<";

    String RIGHT_ANGLE_BRACKET = ">";

    String EQUAL = "=";

    String UNDERLINE = "_";

    String ELLIPSIS = "-";

    String POINT = ".";

    String OUTPUT = "_output";

    String SINGLE_MARK = "'";

    String PERCENT = "%";

    interface Label {

        String DB = "db:";

        String DB_CONFIG = "db:config";

        String DB_MSSQL_CONNECTION = "db:mssql-connection";

        String DB_MY_SQL_CONNECTION = "db:my-sql-connection";

        String DB_ORACLE_CONNECTION = "db:oracle-connection";

        String DB_SQL = "db:sql";

        String DB_INPUT_PARAMETERS = "db:input-parameters";

        String DB_GENERIC_CONNECTION = "db:generic-connection";
    }

    interface Attribute {

        String NAME = "name";

        String DOC_NAME = "doc:name";

        String DOC_ID = "doc:id";

        String HOST = "host";

        String PORT = "port";

        String USER = "user";

        String PASSWORD = "password";

        String INSTANCE = "instance";

        String SERVICE_NAME = "serviceName";

        String DATABASE = "database";

        String DATABASE_NAME = "databaseName";

        String CONFIG_REF = "config-ref";

        String URL = "url";

        String DRIVER_CLASS_NAME = "driverClassName";

        String TARGET = "target";

    }

    interface Dml {
        String INTO = " into ";

        String VALUES = " values ";

        String FROM = " from ";

        String SET = " set ";

        String WHERE = " where ";

        String AND = " and ";

        String OR = " or ";

        String AS = " AS ";
    }

    interface SelectType {

        String TYPE_SINGLE = "single";

        String TYPE_MORE = "more";

    }

    interface Condition {
        String EQUAL = "=";
        String GREATER = ">";
        String LESS = "<";
        String GREATER_EQUAL = ">=";
        String LESS_EQUAL = "<=";
        String NOT_EQUAL = "!=";
        String IN = "in";
        String NOT_IN = "not in";
        String LIKE = "like";
        String NOT_LIKE = "not like";
        String BETWEEN = "between";
        String NOT_BETWEEN = "not between";
        String IS_NULL = "is null";
        String IS_NOT_NULL = "is not null";
    }

    interface ColumnType {

        String VARCHAR = "VARCHAR";

        String INT = "INT";

        String DATE = "DATE";

        String DATETIME = "DATETIME";

        Set<String> SQL_SERVER_INT_TYPE = new HashSet<String>() {
            private static final long serialVersionUID = -1162836689172424478L;

            {
                add("tinyint");
                add("int");
                add("smallint");
                add("decimal");
                add("numeric");
                add("float");
            }
        };

        Set<String> SQL_SERVER_VARCHAR_TYPE = new HashSet<String>() {
            private static final long serialVersionUID = -1162836689172424478L;

            {
                add("char");
                add("varchar");
                add("text");
                add("nvarchar");
                add("nchar");
                add("ntext");
            }
        };

        Set<String> ORACLE_VARCHAR_TYPE = new HashSet<String>() {
            private static final long serialVersionUID = -1162836689172424478L;

            {
                add("char");
                add("nchar");
                add("varchar");
                add("varchar2");
                add("nvarchar2");
            }
        };

        Set<String> ORACLE_INT_TYPE = new HashSet<String>() {
            private static final long serialVersionUID = -1162836689172424478L;

            {
                add("integer");
                add("decimal");
                add("float");
                add("number");
            }
        };
    }

    interface DriverClassName {
        String MYSQL = "com.mysql.cj.jdbc.Driver";

        String ORACLE = "oracle.jdbc.driver.OracleDriver";

        String SQL_SERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }

    interface UrlFormat {
        String MYSQL = "jdbc:mysql://%s:%s/%s";

        String ORACLE = "jdbc:oracle:thin:@%s:%s/%s";

        String JDBC_ORACLE = "jdbc:oracle:thin:@%s:%s/%s";

        String SQL_SERVER = "jdbc:sqlserver://%s:%s;DatabaseName=%s";
    }

    interface DataBaseOperation {
        String PAGE_SELECT = "pageselect";
        String PAGE = "page";
        String PAGESIZE = "pageSize";
        String REQUEST_START_BODY = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" >\n" +
                "    <soapenv:Header></soapenv:Header>\n" +
                "    <soapenv:Body>\n" +
                "        <%s>\n" +
                "            ";
        String REQUEST_END_BODY = "        </%s>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String REQUEST_START = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" >\n" +
                "    <soapenv:Header></soapenv:Header>\n" +
                "    <soapenv:Body>\n" +
                "        <req>\n" +
                "            ";
        String REQUEST_END = "        </req>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String ORACLE = "oracle";
        String SQLSERVER = "sqlserver";
        String MYSQL = "mysql";
        String DATABASE_SELECT = "数据库-查询";
        String DATABASE_INSERT = "数据库-批量插入";
        String DATABASE_UPDATE = "数据库-批量更新";
        String DATABASE_DELETE = "数据库-批量删除";
        String REQUEST_START_SQL = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" >\n" +
                "    <soapenv:Header></soapenv:Header>\n" +
                "    <soapenv:Body>\n" +
                "            ";
        String REQUEST_END_SQL = "\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

    interface SoapFunction {
        String PAYLOAD_HEADERS = "payload.headers.";
        String PAYLOAD_BODY = "payload.body.";
        String XML_HEADER = "%dw 2.0\n" +
                "output application/json duplicateKeyAsArray=true \n" +
                " ns soap http://schemas.xmlsoap.org/soap/envelope/ \n" +
                " --- \n";
        String ENVELOPE_HEADER = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <soapenv:Header>\n";
        String BODY_START = "    <soapenv:Body>\n" +
                "        <operation>";
        String ENVELOPE_BODY = "           </operation>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String TYPE = "type";
        String CODE = "code";
        String TYPE_XSD = "xsd:";
        String CLONED = "\"></";
        String HEADER_CLONED = "</soapenv:Header>";
        String STRING = "string";
        String RESPONSE_BODY_START = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <soap:Body>\n" +
                "      <sucessful>";
        String RESPONSE_BODY_END = "      </sucessful>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";
        String ENVELOPE = "soapenv:Envelope";
        String SOAPENV_HEADER = "soapenv:Header";
        String SOAPENV_BODY = "soapenv:Body";
        String SOAPENV_OPERATION = "operation";
        String SOAP_ENVELOPE = "soap:Envelope";
        String SOAP_BODY = "soap:Body";
        String SOAP_SUCCESSFUL = "sucessful";
        String DATA_FORMAT_HEADER = "\"Header\"";
        String DATA_FORMAT_BODY = "\"Body\"";
        String VALUE = "value";
        String SLASH = "/";
    }

    interface HttpInformation {
        String HTTP_HEADER = "output application/json ---\n";
        String DATA_FORMAT_BODY = ",\"Body\"";
        String DATA_BODY = ":vars.request_output.Body";
        String DATA_FORMAT_QUERY = ",\"Query\"";
        String DATA_QUERY = ":vars.request_output.Query";
        String DATA_FORMAT_PARAM = ",\"Param_path\"";
        String DATA_PARAM = ":vars.request_output.Param_path";
        String DATA_FORMAT_HEADER = "\"Header\"";
        String DATA_HEADER = ":vars.request_output.Header";
    }
}
