-- KEYS[1]表示秒杀secKillId
-- ARGV[1]表示秒杀userId

-- 秒杀的hkey为: secKill_secKillId
-- sale_amount表示剩余库存数, lock_amount表示待支付库存数
-- 秒杀的用户标记key为: secKill_secKillId_user_userId

-- return 1表示成功
-- return 2表示库存不够
-- return 3表示无法重复抢购
-- return 4表示该秒杀信息丢失
local secKillKey = "secKill_" .. ARGV[1];
local saleAmount = "sale_amount";
local lockAmount = "lock_amount";
local secKillUserKey = "secKill_" .. ARGV[1] .. "_user_" .. ARGV[2];

-- 判断秒杀是否超时
if (redis.call('exists', secKillKey) == 1) then
    local stock = tonumber(redis.call('hget', secKillKey, saleAmount));
    -- 判断库存数量
    if(stock > 0) then
        -- 判断是否重复抢购
        if(redis.call('exists', secKillUserKey) == 1) then
            return 3;
        else
            -- 库存扣除
            redis.call('hincrby', secKillKey, saleAmount, -1);
            redis.call('hincrby', secKillKey, lockAmount, 1);

            -- 防止重买的Redis标记设置
            redis.call('set', secKillUserKey, 1);
            return 1;
        end;
    else
        return 2;
    end;
else
    return 4;
end;