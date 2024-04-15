package com.llc.search_service.constants;

public class PaperIndexTemplate {
    public static final String PAPER_INDEX_TEMPLATE = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"name\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"grade\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"discipline\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"grade_category\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"version\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"final_aim\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"review_test\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"is_real\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"exam_name\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"school\": {\n" +
            "        \"properties\": {\n" +
            "          \"school_name\": {\n" +
            "            \"type\": \"text\",\n" +
            "            \"analyzer\": \"ik_smart\",\n" +
            "            \"fields\": {\n" +
            "              \"keyword\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          },\n" +
            "          \"school_address\": {\n" +
            "            \"type\": \"text\",\n" +
            "            \"analyzer\": \"ik_smart\",\n" +
            "            \"fields\": {\n" +
            "              \"keyword\": {\n" +
            "                \"type\": \"keyword\"\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"province\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"city\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"districts\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"download_count\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"update_time\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"url\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"is_vip\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"expense\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}\n";
}
