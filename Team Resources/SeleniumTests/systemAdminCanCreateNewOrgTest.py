########################
#   Imports
########################
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time



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

# Go to system admin home via System Admin Nav Bar
def goToSystemAdminPageUsingNavBar():
    systemAdminButton = driver.find_element(By.XPATH,'//*[@id="navbarSupportedContent"]/ul/li[4]/a')
    systemAdminButton.click()
    assert "System Admin" in driver.title

# Go to Register New Org Admin via System Admin NavBar
def goToSystemAdminRegisterNewAdminPageUsingNavBar():
    systemAdminNewOrgButton = driver.find_element(By.XPATH,'//*[@id="navbarSupportedContent"]/ul/li[6]/a')
    systemAdminNewOrgButton.click()
    assert "New Organisation" in driver.title



########################
# Create Org Page Navigation Functions
########################
def createNewOrganisation():
    newOrgNameInput = driver.find_element(By.XPATH,'//*[@id="organisationNameInput"]')
    newOrgEmailInput = driver.find_element(By.XPATH,'//*[@id="emailInput"]')
    newOrgSubmitButton = driver.find_element(By.XPATH,'/html/body/div/div/form/div/div[2]/button')

    newOrgNameInput.send_keys('The Selenium Test Company')
    newOrgEmailInput.send_keys('Selenium@SeleniumTest.com')
    newOrgSubmitButton.click()
    print("Register New Organisation Form Submitted")
    time.sleep(3)


def checkForSuccessMessage():
    responseMessage = driver.find_element(By.XPATH,'/html/body/div/div/div')
    if "already exists" in responseMessage.text:
        print("Already Exists")
    elif "has been saved" in responseMessage.text:
        print("Success")
    else:
        print("FAIL")
        raise ValueError("Success Message Did Not Exist")



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
        createNewOrganisation()
        print("Passed Stage 5 / 6")
        checkForSuccessMessage()
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
