########################
#   Imports
########################
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time
import random




########################
# Variables
########################
driver = webdriver.Chrome('./chromedriver')
driver.fullscreen_window()



########################
# General Navigation Functions
########################
# Get pass Google's 'connection not provate' page (cuz Https)
def byPassConnectionNotPrivate():
    driver.get("https://localhost:8443/")

    advanceSafetyOptions = driver.find_element(By.XPATH,'//*[@id="details-button"]');
    advanceSafetyOptions.click()

    proceedButton = driver.find_element(By.XPATH,'//*[@id="proceed-link"]');
    proceedButton.click()


# Enter Our page
def startUp():
    driver.get("https://localhost:8443/")
    assert "Graphium" in driver.title
    driver.fullscreen_window()



########################
# System Admin Navigation Functions
########################
# Login as a System Admin
def logInAsSystemAdmin():
    usernameField = driver.find_element(By.XPATH,"/html/body/form/div[2]/input")
    passwordField = driver.find_element(By.XPATH,"/html/body/form/div[3]/input")
    submitButton = driver.find_element(By.XPATH,"/html/body/form/div[4]/button")

    usernameField.send_keys('testSystemAdmin')
    passwordField.send_keys('password')

    submitButton.click()

    assert "Welcome" in driver.title

def goToSystemAdminRegisterNewAdminPageUsingNavBar():
    systemAdminNewOrgButton = driver.find_element(By.XPATH,'/html/body/nav/nav/div/div[2]/ul/li[4]/a')
    systemAdminNewOrgButton.click()
    assert "new admin" in driver.title

def submitNewOrgAdminAccount():
    usernameField = driver.find_element(By.XPATH,'//*[@id="username"]')
    passwordField = driver.find_element(By.XPATH,'//*[@id="password"]')
    emailField = driver.find_element(By.XPATH,'//*[@id="email"]')
    firstNameField = driver.find_element(By.XPATH,'//*[@id="firstName"]')
    lastNameField = driver.find_element(By.XPATH,'//*[@id="lastName"]')
    organisationsField = driver.find_element(By.XPATH,'//*[@id="orgId"]')
    submitButton = driver.find_element(By.XPATH,'/html/body/div/form/fieldset/div[7]/div/button')

    usernameField.send_keys('test' + str(random.randint(0,1000000)))
    passwordField.send_keys('aVeryComplexPassword')
    emailField.send_keys('Selenium2' + str(random.randint(0,10000)) + '@Selinum.com')
    firstNameField.send_keys('SeleniumTest')
    lastNameField.send_keys('SeleniumTest')
    organisationsField.send_keys('1')

    driver.execute_script("arguments[0].scrollIntoView();", submitButton)
    time.sleep(3)
    submitButton.click()
    time.sleep(3)

def submitSuccessRedirectsToHome():
    successMessage = driver.find_element(By.XPATH,'/html/body/div/div/h1')

    if "Account Created successfully!" in successMessage.text:
        return True
    else:
        raise ValueError("Success Message Did Not Exist")
        return False

########################
# System Admin Tests
#########################
# System Admin can create new Organisation Admin Account
def systemAdminCanCreateNewOrganisationAdmin():
    print("Starting Test: System Admin Can Create New Organisation Admin")
    try:
        byPassConnectionNotPrivate()
        print("Passed Stage 1 / 6")
        startUp()
        print("Passed Stage 2 / 6")
        logInAsSystemAdmin()
        print("Passed Stage 3 / 6")
        goToSystemAdminRegisterNewAdminPageUsingNavBar()
        print("Passed Stage 4 / 6")
        submitNewOrgAdminAccount()
        print("Passed Stage 5 / 6")
        submitSuccessRedirectsToHome()
        print("Passed Stage 6 / 6")
        print("Test Passed")
    except:
        print("Error - Test Failed")



########################
# Calling Tests
########################
systemAdminCanCreateNewOrganisationAdmin()



########################
# End
########################
driver.close()
