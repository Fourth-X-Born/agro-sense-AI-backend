# Admin API Test Script
# Run this in PowerShell to test all admin endpoints

$baseUrl = "http://localhost:8080/api/admin"
$headers = @{"Content-Type" = "application/json"}

Write-Host "`n========== ADMIN API TESTS ==========`n" -ForegroundColor Cyan

# ==================== CROPS ====================
Write-Host "1. GET /api/admin/crops (List all crops)" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/crops" -Method GET
    Write-Host "   SUCCESS: Found $($response.Count) crops" -ForegroundColor Green
    $response | ForEach-Object { Write-Host "   - ID: $($_.id), Name: $($_.name)" }
} catch {
    Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n2. POST /api/admin/crops (Create crop)" -ForegroundColor Yellow
try {
    $body = '{"name": "TestCrop_API"}'
    $response = Invoke-RestMethod -Uri "$baseUrl/crops" -Method POST -Body $body -ContentType "application/json"
    Write-Host "   SUCCESS: Created crop ID=$($response.id), Name=$($response.name)" -ForegroundColor Green
    $newCropId = $response.id
} catch {
    Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
    $newCropId = $null
}

Write-Host "`n3. PUT /api/admin/crops/{id} (Update crop)" -ForegroundColor Yellow
if ($newCropId) {
    try {
        $body = '{"name": "TestCrop_Updated"}'
        $response = Invoke-RestMethod -Uri "$baseUrl/crops/$newCropId" -Method PUT -Body $body -ContentType "application/json"
        Write-Host "   SUCCESS: Updated crop ID=$($response.id), Name=$($response.name)" -ForegroundColor Green
    } catch {
        Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host "`n4. DELETE /api/admin/crops/{id} (Delete crop)" -ForegroundColor Yellow
if ($newCropId) {
    try {
        $response = Invoke-RestMethod -Uri "$baseUrl/crops/$newCropId" -Method DELETE
        Write-Host "   SUCCESS: $($response.message)" -ForegroundColor Green
    } catch {
        Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
    }
}

# ==================== DISTRICTS ====================
Write-Host "`n5. GET /api/admin/districts (List all districts)" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/districts" -Method GET
    Write-Host "   SUCCESS: Found $($response.Count) districts" -ForegroundColor Green
    $response | Select-Object -First 3 | ForEach-Object { Write-Host "   - ID: $($_.id), Name: $($_.name)" }
} catch {
    Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n6. POST /api/admin/districts (Create district)" -ForegroundColor Yellow
try {
    $body = '{"name": "TestDistrict_API"}'
    $response = Invoke-RestMethod -Uri "$baseUrl/districts" -Method POST -Body $body -ContentType "application/json"
    Write-Host "   SUCCESS: Created district ID=$($response.id), Name=$($response.name)" -ForegroundColor Green
    $newDistrictId = $response.id
} catch {
    Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
    $newDistrictId = $null
}

Write-Host "`n7. PUT /api/admin/districts/{id} (Update district)" -ForegroundColor Yellow
if ($newDistrictId) {
    try {
        $body = '{"name": "TestDistrict_Updated"}'
        $response = Invoke-RestMethod -Uri "$baseUrl/districts/$newDistrictId" -Method PUT -Body $body -ContentType "application/json"
        Write-Host "   SUCCESS: Updated district ID=$($response.id), Name=$($response.name)" -ForegroundColor Green
    } catch {
        Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host "`n8. DELETE /api/admin/districts/{id} (Delete district)" -ForegroundColor Yellow
if ($newDistrictId) {
    try {
        $response = Invoke-RestMethod -Uri "$baseUrl/districts/$newDistrictId" -Method DELETE
        Write-Host "   SUCCESS: $($response.message)" -ForegroundColor Green
    } catch {
        Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
    }
}

# ==================== MARKET PRICES ====================
Write-Host "`n9. GET /api/admin/market-prices (List all market prices)" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/market-prices" -Method GET
    Write-Host "   SUCCESS: Found $($response.Count) market prices" -ForegroundColor Green
} catch {
    Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n10. POST /api/admin/market-prices (Create market price)" -ForegroundColor Yellow
try {
    $body = '{"cropId": 1, "districtId": 1, "pricePerKg": 125.50, "priceDate": "2026-02-08"}'
    $response = Invoke-RestMethod -Uri "$baseUrl/market-prices" -Method POST -Body $body -ContentType "application/json"
    Write-Host "   SUCCESS: Created market price ID=$($response.id)" -ForegroundColor Green
    $newPriceId = $response.id
} catch {
    Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
    $newPriceId = $null
}

Write-Host "`n11. PUT /api/admin/market-prices/{id} (Update market price)" -ForegroundColor Yellow
if ($newPriceId) {
    try {
        $body = '{"cropId": 1, "districtId": 1, "pricePerKg": 150.00, "priceDate": "2026-02-08"}'
        $response = Invoke-RestMethod -Uri "$baseUrl/market-prices/$newPriceId" -Method PUT -Body $body -ContentType "application/json"
        Write-Host "   SUCCESS: Updated market price ID=$($response.id)" -ForegroundColor Green
    } catch {
        Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host "`n12. DELETE /api/admin/market-prices/{id} (Delete market price)" -ForegroundColor Yellow
if ($newPriceId) {
    try {
        $response = Invoke-RestMethod -Uri "$baseUrl/market-prices/$newPriceId" -Method DELETE
        Write-Host "   SUCCESS: $($response.message)" -ForegroundColor Green
    } catch {
        Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
    }
}

# ==================== FERTILIZERS ====================
Write-Host "`n13. GET /api/admin/fertilizers (List all fertilizers)" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/fertilizers" -Method GET
    Write-Host "   SUCCESS: Found $($response.Count) fertilizer recommendations" -ForegroundColor Green
} catch {
    Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n14. POST /api/admin/fertilizers (Create fertilizer)" -ForegroundColor Yellow
try {
    $body = @{
        cropId = 1
        fertilizerName = "TestFertilizer"
        fertilizerType = "Nitrogen"
        dosagePerHectare = "100 kg/ha"
        applicationStage = "Growth"
        applicationMethod = "Broadcasting"
        notes = "Test note"
    } | ConvertTo-Json
    $response = Invoke-RestMethod -Uri "$baseUrl/fertilizers" -Method POST -Body $body -ContentType "application/json"
    Write-Host "   SUCCESS: Created fertilizer ID=$($response.id), Name=$($response.fertilizerName)" -ForegroundColor Green
    $newFertilizerId = $response.id
} catch {
    Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
    $newFertilizerId = $null
}

Write-Host "`n15. PUT /api/admin/fertilizers/{id} (Update fertilizer)" -ForegroundColor Yellow
if ($newFertilizerId) {
    try {
        $body = @{
            cropId = 1
            fertilizerName = "TestFertilizer_Updated"
            fertilizerType = "Phosphorus"
            dosagePerHectare = "150 kg/ha"
            applicationStage = "Flowering"
            applicationMethod = "Foliar spray"
            notes = "Updated note"
        } | ConvertTo-Json
        $response = Invoke-RestMethod -Uri "$baseUrl/fertilizers/$newFertilizerId" -Method PUT -Body $body -ContentType "application/json"
        Write-Host "   SUCCESS: Updated fertilizer ID=$($response.id), Name=$($response.fertilizerName)" -ForegroundColor Green
    } catch {
        Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host "`n16. DELETE /api/admin/fertilizers/{id} (Delete fertilizer)" -ForegroundColor Yellow
if ($newFertilizerId) {
    try {
        $response = Invoke-RestMethod -Uri "$baseUrl/fertilizers/$newFertilizerId" -Method DELETE
        Write-Host "   SUCCESS: $($response.message)" -ForegroundColor Green
    } catch {
        Write-Host "   ERROR: $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host "`n========== TESTS COMPLETE ==========`n" -ForegroundColor Cyan
