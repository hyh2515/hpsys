hpsys
����hpsys-admin-web              -- ǰ�����(vite���+vue)
��  ��  index.html               -- ��ҳ��
��  ����src
��      ��  App.vue
��      ��  hpsys.js             -- ǰ��ȫ��js
��      ��  main.js
��      ����api                   -- ���Ӻ�˵�api�ӿ�
��      ����components            -- �����ҳ���и���
��      ����config                -- �������ã�����Ŀ���ơ���˽ӿڵ�ַ����Ŀlogo��ַ��
��      ����layout                -- ���岼��
��      ����router                -- ·����ת
��      ����store                 -- ״̬����ʹ��pinia�־û��洢
��      ����style                 -- ҳ�����
��      ����utils                 -- js�����ļ��У��������ļ�����
��      ����views                 -- չʾ��ͼ��ҳ������
��          ����house             -- ס����Ϣ��չʾ�������ƺ�Ԥ���ҳ��
��          ����index             -- ��ҳ
��          ����login             -- ��¼ҳ��
��          ����module            -- �㷨ģ�͹������ã�
��          ����user              -- �û�����
����src                          -- Java���
   ����main
      ����java
      ��  ����com
      ��      ����shou
      ��          ����hpsys
      ��              ��  HpsysApplication.java  -- ������
      ��              ��  
      ��              ����common                  -- ͨ���ļ���
      ��              ��  ����annotation           -- ͨ��ע��
      ��              ��  ����cache                -- ͨ�û��棬�洢��redis��
      ��              ��  ����enums                -- ͨ��ö��
      ��              ��  ����exception            -- ͨ���쳣
      ��              ��  ����handler              -- ���������㷨��SM4���ܼ��ܣ�
      ��              ��  ����page                 -- ͨ�÷�ҳ��ǰ��չʾ��Ϣʹ�÷�ҳ��ʽ��
      ��              ��  ����pojo                 -- ͨ���ࣨ��ʵ�壬������̳У�
      ��              ��  ����utils                -- ͨ�ù�����
      ��              ����config                  -- �ӿ�����
      ��              ����handler                 -- ����ǰ�˴��ݵ��쳣������
      ��              ����web                     -- ǰ�������
      ��                  ����login               -- ����ǰ�˵�¼ҵ��
      ��                  ����menu                -- �˵�ҵ��
      ��                  ����predict             -- Ԥ��ͷ�����Ϣ
      ��                  ����relation            -- ���ݿ�ʵ���ϵ
      ��                  ����role                -- ��ɫ����
      ��                  ����user                -- �û�����
      ����resources                              -- ��Դ�ļ���
          ��  application.yml                   -- ��Ŀ����
          ��  logback-spring.xml                -- ��־����
          ����pmml                               -- pythonѵ����ģ�ͣ�ʹ��PMML�ļ���ʽ����
                 lasso_model.pmml
                 line_model.pmml
                 ridge_model.pmml
