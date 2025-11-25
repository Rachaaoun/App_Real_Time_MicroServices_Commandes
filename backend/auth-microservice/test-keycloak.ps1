# -------------------------------
# Configuration
# -------------------------------
$realmUrl       = "http://localhost:8280/realms/myrealm/protocol/openid-connect/token"
$clientId       = "spring-auth-api"
$clientSecret   = "1JRFrEuc50t72rIuh0HDVh3Uo4DcrgR7"
$username       = "rachatest"
$password       = "1234"

$endpointUrl    = "http://localhost:9000/secure/hello"

# -------------------------------
# Récupération du token JWT
# -------------------------------
$body = @{
    grant_type    = "password"
    client_id     = $clientId
    client_secret = $clientSecret
    username      = $username
    password      = $password
}

try {
    $response = Invoke-RestMethod -Method Post -Uri $realmUrl -Body $body
    $token = $response.access_token
    Write-Host "Token récupéré avec succès :"
    Write-Host $token
} catch {
    Write-Error "Erreur lors de la récupération du token : $_"
    exit
}

# -------------------------------
# Appel de l'endpoint sécurisé
# -------------------------------
try {
    $secureResponse = Invoke-RestMethod -Method Get -Uri $endpointUrl -Headers @{ Authorization = "Bearer $token" }
    Write-Host "`nRéponse de l'endpoint sécurisé :"
    Write-Host $secureResponse
} catch {
    Write-Error "Erreur lors de l'appel à l'endpoint sécurisé : $_"
}
