const { Given, When, Then, Before, After } = require('@cucumber/cucumber');
const { chromium } = require('@playwright/test');
const assert = require('assert');

let browser;
let context;
let page;

function baseUrl(world) {
  return (world && world.parameters && world.parameters.baseUrl) || 'http://localhost:8080';
}

function isHeadless() {
  // Default: headless. Set HEADLESS=false to run headed.
  return String(process.env.HEADLESS || '').toLowerCase() !== 'false';
}

Before(async function () {
  browser = await chromium.launch({ headless: isHeadless() });
  context = await browser.newContext();
  page = await context.newPage();
});

Given('the application is running', async function () {
  // We assume the user started the Spring Boot app separately.
});

When('I open the login page', async function () {
  await page.goto(`${baseUrl(this)}/login`, { waitUntil: 'domcontentloaded' });
});

When('I open the welcome page directly', async function () {
  await page.goto(`${baseUrl(this)}/welcome`, { waitUntil: 'domcontentloaded' });
});

When('I log in with username {string} and password {string}', async function (username, password) {
  await page.fill('input#username', username);
  await page.fill('input#password', password);

  await Promise.all([
    page.waitForNavigation({ waitUntil: 'domcontentloaded' }),
    page.click('button[type="submit"]'),
  ]);
});

When('I log out', async function () {
  // Logout is a POST form with a submit button.
  await Promise.all([
    page.waitForNavigation({ waitUntil: 'domcontentloaded' }),
    page.click('button.logout-link'),
  ]);
});

Then('I should see the welcome page with username {string}', async function (username) {
  await page.waitForURL(/\/welcome/);

  const title = await page.textContent('h1');
  assert.ok(title && title.includes('Welcome'), 'Expected welcome title');

  const content = await page.content();
  assert.ok(content.includes(username), `Expected page to include username: ${username}`);
});

Then('I should see an invalid credentials error', async function () {
  // Spring Security typically redirects back to /login?error on invalid credentials.
  await page.waitForURL(/\/login(\?.*)?$/);

  const content = await page.content();
  assert.ok(
    /invalid/i.test(content) || /bad credentials/i.test(content) || /\?error\b/.test(page.url()),
    'Expected an invalid credentials error to be displayed'
  );
});

Then('I should be redirected to the login page', async function () {
  await page.waitForURL(/\/login(\?.*)?$/);
});

After(async function () {
  if (browser) {
    await browser.close();
  }
  browser = undefined;
  context = undefined;
  page = undefined;
});
