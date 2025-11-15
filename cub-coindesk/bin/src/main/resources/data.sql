
INSERT INTO currency(code, name_zh) VALUES
 ('USD','美元'),
 ('GBP','英鎊'),
 ('EUR','歐元')
 ON CONFLICT DO NOTHING;
