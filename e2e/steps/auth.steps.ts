import { Before, After, Given, When, Then } from '@cucumber/cucumber';
import { chromium, Browser, Page } from '@playwright/test';
import assert from 'node:assert/strict';

const BASE_URL = process.env.E2E_BASE_URL ?? 'http://localhost:8085';

let browser: Browser;
let page: Page;

Before(async () => {
  browser = await chromium.launch();
  page = await browser.newPage();
});

After(async () => {
  await page.close();
  await browser.close();
});

Given('the user is on the login page', async () => {
  await page.goto(`${BASE_URL}/login`);
});

Given('the user is not authenticated', async () => {
  // Fresh browser context per scenario means no cookies/session.
});

Given('the user is logged in', async () => {
  await page.goto(`${BASE_URL}/login`);
  await page.getByLabel('Username').fill(process.env.E2E_VALID_USERNAME ?? 'user');
  await page.getByLabel('Password').fill(process.env.E2E_VALID_PASSWORD ?? 'password');
  await page.getByRole('button', { name: /sign in/i }).click();
  await page.waitForURL(/\/welcome$/);
});

When('the user logs in with valid credentials', async () => {
  await page.getByLabel('Username').fill(process.env.E2E_VALID_USERNAME ?? 'user');
  await page.getByLabel('Password').fill(process.env.E2E_VALID_PASSWORD ?? 'password');
  await page.getByRole('button', { name: /sign in/i }).click();
});

When('the user logs in with invalid credentials', async () => {
  await page.getByLabel('Username').fill('wrong');
  await page.getByLabel('Password').fill('wrong');
  await page.getByRole('button', { name: /sign in/i }).click();
});

When('the user navigates to the welcome page', async () => {
  await page.goto(`${BASE_URL}/welcome`);
});

When('the user logs out', async () => {
  // Prefer POST logout via form button
  await page.getByRole('button', { name: /logout/i }).click();
});

Then('the user should see the welcome page', async () => {
  await page.waitForURL(/\/welcome$/);
  await assertPageContains(/welcome/i);
});

Then('the user should remain on the login page', async () => {
  await page.waitForURL(/\/login/);
});

Then('the user should see an invalid credentials error', async () => {
  await assertPageContains(/invalid/i);
});

Then('the user should be redirected to the login page', async () => {
  await page.waitForURL(/\/login/);
});

async function assertPageContains(re: RegExp) {
  const body = await page.locator('body').innerText();
  assert.match(body, re);
}
