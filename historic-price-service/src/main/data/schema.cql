--/cqlsh -f ~/Documents/dev/repos/fondue/historic-price-service/src/main/data/schema.cql
DROP KEYSPACE IF EXISTS fondue;

CREATE KEYSPACE fondue WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor':1};

USE fondue;

CREATE TABLE prices (
  instrument text,
  time timestamp,
  ask double,
  bid double,
  provider text,
  PRIMARY KEY (instrument, time)
) WITH
  bloom_filter_fp_chance=0.010000 AND
  caching='KEYS_ONLY' AND
  comment='' AND
  dclocal_read_repair_chance=0.000000 AND
  gc_grace_seconds=864000 AND
  index_interval=128 AND
  read_repair_chance=0.100000 AND
  replicate_on_write='true' AND
  populate_io_cache_on_flush='false' AND
  default_time_to_live=0 AND
  speculative_retry='99.0PERCENTILE' AND
  memtable_flush_period_in_ms=0 AND
  compaction={'class': 'SizeTieredCompactionStrategy'} AND
  compression={'sstable_compression': 'LZ4Compressor'};
  
  CREATE TABLE histograms (
  instrument text,
  time timestamp,
  volume bigint,
  granularity text,
  openAsk double,
  openBid double,
  highAsk double,
  highBid double,
  lowAsk double,
  lowBid double,
  closeBid double,
  closeAsk double,
  provider text,
  PRIMARY KEY (instrument, time)
) WITH
  bloom_filter_fp_chance=0.010000 AND
  caching='KEYS_ONLY' AND
  comment='' AND
  dclocal_read_repair_chance=0.000000 AND
  gc_grace_seconds=864000 AND
  index_interval=128 AND
  read_repair_chance=0.100000 AND
  replicate_on_write='true' AND
  populate_io_cache_on_flush='false' AND
  default_time_to_live=0 AND
  speculative_retry='99.0PERCENTILE' AND
  memtable_flush_period_in_ms=0 AND
  compaction={'class': 'SizeTieredCompactionStrategy'} AND
  compression={'sstable_compression': 'LZ4Compressor'};