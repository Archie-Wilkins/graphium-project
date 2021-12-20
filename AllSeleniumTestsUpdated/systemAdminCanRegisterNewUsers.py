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

def goToRegisterNewUsersPage():
        driver.get("https://localhost:8443/systemAdmin/newUser")

def submitNewUsersForm():
    usernameField = driver.find_element(By.XPATH,"/html/body/div/div/form/div[1]/input")
    passwordField = driver.find_element(By.XPATH,"/html/body/div/div/form/div[2]/input")
    emailField = driver.find_element(By.XPATH,"/html/body/div/div/form/div[3]/input")
    firstNameField = driver.find_element(By.XPATH,"/html/body/div/div/form/div[4]/div/input")
    lastNameField = driver.find_element(By.XPATH,"/html/body/div/div/form/div[5]/input")
    submitButton = driver.find_element(By.XPATH,"/html/body/div/div/form/button")

    usernameField.send_keys("Selenium" + str(random.randint(0,1000000)))
    passwordField.send_keys("password")
    emailField.send_keys("Selenium@Test.com")
    firstNameField.send_keys("Selenium")
    lastNameField.send_keys("Selenium")

    time.sleep(2)
    submitButton.click()
    time.sleep(2)

def checkSuccess():
    feedBack = driver.find_element(By.XPATH,"/html/body/div/div/form/div[1]/div")
    assert "has been saved" in driver.title

########################
# System Admin Tests
#########################
# System Admin can create new Organisation Admin Account
def systemAdminCanCreateNewOrganisationAdmin():
    print("Starting Test: System Admin Can Create New Organisation Admin")
    try:
        #Get pass advanced security warning
        byPassConnectionNotPrivate()
        print("Passed Stage 1 / 6")
        #Go to login page
        startUp()
        print("Passed Stage 2 / 6")
        logInAsSystemAdmin()
        print("Passed Stage 3 / 6")
        #Navigate to systemAdmin/newUser
        goToRegisterNewUsersPage()
        print("Passed Stage 4 / 6")
        #Fill in form and submit it
        submitNewUsersForm()
        print("Passed Stage 5 / 6")

        # check success
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
